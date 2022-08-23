package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CarRentalTimeJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.OrderJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.OrderCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CarRentalTimeRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CarRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CategoryRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.ModelRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.OrderRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.PriceRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserRepository;
import com.dmdev.natalliavasilyeva.service.exception.NotFoundException;
import com.dmdev.natalliavasilyeva.service.exception.OrderBadRequestException;
import com.dmdev.natalliavasilyeva.service.exception.UserBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final int INSURANCE_PERCENT = 5;

    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();
    ServiceFactory serviceFactory = ServiceFactory.getInstance();

    OrderRepository orderRepository = repositoryFactory.getOrderRepository();
    OrderCustomRepository orderCustomRepository = repositoryCustomFactory.getOrderCustomRepository();
    CarRepository carRepository = repositoryFactory.getCarRepository();
    UserRepository userRepository = repositoryFactory.getUserRepository();
    ModelRepository modelRepository = repositoryFactory.getModelRepository();
    CategoryRepository categoryRepository = repositoryFactory.getCategoryRepository();
    PriceRepository priceRepository = repositoryFactory.getPriceRepository();
    CarRentalTimeRepository carRentalTimeRepository = repositoryFactory.getCarRentalTimeRepository();

    DriverLicenseService driverLicenseService = serviceFactory.getDriverLicenseService();

    public Order createOrder(Order order) {
        var carJpa = ensureCarExistsById(order.getCar().getId());
        var carRentalTimeJpa = OrderMapper.toCarRentalTimeJpa(order);
        if (ensureCarAvailable(carRentalTimeJpa, carJpa)) {
            var userJpa = ensureUserExistsById(order.getUser().getId());
            if (driverLicenseService.isDriverLicenseExpired(userJpa.getId())) {
                BigDecimal price = findPriceByOrder(order).orElseThrow(() -> new RuntimeException("Do not find price for this car"));
                int numberOfDays = calculateNumberOfDays(carRentalTimeJpa);
                var jpa = OrderMapper.toOrderJpa(order);
                BigDecimal orderSum = jpa.isInsuranceNeeded() ? calculateOrderSumWithInsurance(numberOfDays, price, calculateInsuranceSum(numberOfDays, price)) :
                        calculateOrderSumWithoutInsurance(numberOfDays, price);
                jpa.setSum(orderSum);

                Optional<OrderJpa> savedOrderJpa = orderRepository.save(jpa);
                if (savedOrderJpa.isPresent()) {
                    carRentalTimeJpa.setOrderId(savedOrderJpa.get().getId());
                    Optional<CarRentalTimeJpa> savedCarRentalTime = carRentalTimeRepository.save(carRentalTimeJpa);
                    if (savedCarRentalTime.isPresent()) {
                        return OrderMapper.fromJpa(savedOrderJpa.get(), savedCarRentalTime.get(), userJpa, carJpa);
                    }
                } else {
                    throw new RuntimeException("Problem with order saving");
                }
            } else {
                throw new UserBadRequestException("Driver license is expired or licence doesn't exist for this user");
            }
        }
        throw new OrderBadRequestException("Car is not available for these dates");
    }

    public Order updateOrder(Long id, Order order) {
        var existingOrder = ensureOrderExistsById(id);
        var existingCarRentalTime = ensureCarRentalTimeExistsByOrderId(id);
        var existingCar = ensureCarExistsById(order.getCar().getId());
        var existingUser = ensureUserExistsById(order.getUser().getId());

        existingOrder.setUserId(order.getUser().getId());
        existingOrder.setCarId(order.getCar().getId());
        existingOrder.setPassport(order.getPassport());

        boolean isInsuranceNeededExisting = existingOrder.isInsuranceNeeded();
        boolean isInsuranceNeededToUpdate = order.isInsuranceNeeded();
        var carRentalTimeJpaToUpdate = OrderMapper.toCarRentalTimeJpa(order);

        if (!ensureCarAvailableByOrderId(existingOrder.getId(), carRentalTimeJpaToUpdate, existingCar)) {
           throw  new OrderBadRequestException("Car is unavailable for this dates");
        }

        if (isRentalDateChanged(carRentalTimeJpaToUpdate, existingCarRentalTime) ||
                isInsuranceNeededExisting != isInsuranceNeededToUpdate) {
            BigDecimal price = findPriceByOrder(order).orElseThrow(() -> new NotFoundException("Do not find price for this car"));
            int numberOfDays = calculateNumberOfDays(carRentalTimeJpaToUpdate);
            if (isInsuranceNeededToUpdate) {
                BigDecimal insuranceSum = calculateInsuranceSum(numberOfDays, price);
                existingOrder.setSum(calculateOrderSumWithInsurance(numberOfDays, price, insuranceSum));
            } else {
                existingOrder.setSum(calculateOrderSumWithoutInsurance(numberOfDays, price));
            }
            existingCarRentalTime.setStartRentalDate(order.getCarRentalTime().getStartRentalDate());
            existingCarRentalTime.setEndRentalDate(order.getCarRentalTime().getEndRentalDate());
        }

        var updatedOrder = orderRepository.update(existingOrder);
        var updatedCarRentalTime = carRentalTimeRepository.update(existingCarRentalTime);
        if (updatedOrder.isPresent() && updatedCarRentalTime.isPresent()) {
            return OrderMapper.fromJpa(updatedOrder.get(), updatedCarRentalTime.get(), existingUser, existingCar);
        }
        throw new RuntimeException("Problem with order updating");
    }

    public Order updateOrderByUser(Long id, Order order) {
        var existingOrder = ensureOrderExistsById(id);
        var existingCarRentalTime = ensureCarRentalTimeExistsByOrderId(id);
        var existingCar = ensureCarExistsById(existingOrder.getCarId());
        var existingUser = ensureUserExistsById(existingOrder.getUserId());

        var carRentalTimeJpaToUpdate = OrderMapper.toCarRentalTimeJpa(order);

        if (!ensureCarAvailableByOrderId(existingOrder.getId(), carRentalTimeJpaToUpdate, existingCar)) {
            throw  new OrderBadRequestException("Car is unavailable for this dates");
        }

        boolean isInsuranceNeededExisting = existingOrder.isInsuranceNeeded();
        boolean isInsuranceNeededToUpdate = order.isInsuranceNeeded();

        if (isRentalDateChanged(carRentalTimeJpaToUpdate, existingCarRentalTime) ||
                isInsuranceNeededExisting != isInsuranceNeededToUpdate) {
            BigDecimal price = findPriceByOrderJpa(existingOrder).orElseThrow(() -> new NotFoundException("Do not find price for this car"));
            int numberOfDays = calculateNumberOfDays(carRentalTimeJpaToUpdate);
            if (isInsuranceNeededToUpdate) {
                BigDecimal insuranceSum = calculateInsuranceSum(numberOfDays, price);
                existingOrder.setSum(calculateOrderSumWithInsurance(numberOfDays, price, insuranceSum));
                existingOrder.setInsuranceNeeded(true);
            } else {
                existingOrder.setSum(calculateOrderSumWithoutInsurance(numberOfDays, price));
                existingOrder.setInsuranceNeeded(false);
            }
            existingCarRentalTime.setStartRentalDate(order.getCarRentalTime().getStartRentalDate());
            existingCarRentalTime.setEndRentalDate(order.getCarRentalTime().getEndRentalDate());
        }

        var updatedOrder = orderRepository.update(existingOrder);
        var updatedCarRentalTime = carRentalTimeRepository.update(existingCarRentalTime);
        if (updatedOrder.isPresent() && updatedCarRentalTime.isPresent()) {
            return OrderMapper.fromJpa(updatedOrder.get(), updatedCarRentalTime.get(), existingUser, existingCar);
        }
        throw new RuntimeException("Problem with order updating");
    }

    public Order changeOrderStatus(Long id, String status) {
        var existingOrder = ensureOrderExistsById(id);
        var existingCarRentalTime = ensureCarRentalTimeExistsByOrderId(id);
        var existingCar = ensureCarExistsById(existingOrder.getCarId());
        var existingUser = ensureUserExistsById(existingOrder.getUserId());

        existingOrder.setOrderStatus(OrderStatus.getEnum(status));
        var updatedOrder = orderRepository.update(existingOrder);
        if (status.equals(OrderStatus.CANCELLED.name()) || status.equals(OrderStatus.DECLINED.name())) {
            carRentalTimeRepository.deleteById(existingCarRentalTime.getId());
        }
        if (updatedOrder.isPresent()) {
            return OrderMapper.fromJpa(updatedOrder.get(), existingCarRentalTime, existingUser, existingCar);
        }
        throw new RuntimeException("Problem with status updating");
    }

    public Order getOrderById(Long id) {
        OrderJpa orderJpa = ensureOrderExistsById(id);
        CarRentalTimeJpa carRentalTimeJpa = ensureCarRentalTimeExistsByOrderId(id);
        UserJpa userJpa = ensureUserExistsById(orderJpa.getUserId());
        CarJpa carJpa = ensureCarExistsById(orderJpa.getCarId());
        return OrderMapper.fromJpa(orderJpa, carRentalTimeJpa, userJpa, carJpa);
    }

    public Order getCustomOrderById(Long id) {
        return orderCustomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id %s does not exist.", id)));
    }

    public List<Order> getAllOrders() {
        List<OrderJpa> orderJpaList = orderRepository.findAll();
        List<CarRentalTimeJpa> carRentalTimeJpaList = carRentalTimeRepository.findAll();
        List<UserJpa> userJpaList = userRepository.findAll();
        List<CarJpa> carJpaList = carRepository.findAll();

        return OrderMapper.fromJpaList(orderJpaList, carRentalTimeJpaList, userJpaList, carJpaList)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> getAllCustomOrders() {
        return orderCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public boolean deleteOrderById(Long id) {
        ensureOrderExistsById(id);
        CarRentalTimeJpa carRentalTimeJpa = ensureCarRentalTimeExistsByOrderId(id);
        boolean rentalTimeResult = carRentalTimeRepository.deleteById(carRentalTimeJpa.getId());
        boolean orderResult = orderRepository.deleteById(id);
        return rentalTimeResult && orderResult;
    }

    public Order deleteOrder(Order order) {
        ensureOrderExistsById(order.getId());
        ensureCarRentalTimeExistsByOrderId(order.getId());
        Optional<CarRentalTimeJpa> rentalTimeResult = carRentalTimeRepository.delete(OrderMapper.toCarRentalTimeJpa(order));
        Optional<OrderJpa> orderResult = orderRepository.delete(OrderMapper.toOrderJpa(order));

        if (rentalTimeResult.isPresent() && orderResult.isPresent()) {
            return order;
        } else {
            throw new RuntimeException("Problem with order deleting");
        }
    }

    public List<Order> findAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUser(userId)
                .stream()
                .map(OrderMapper::fromJpa)
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersByUserId(Long userId) {
        return orderCustomRepository.findAllByUser(userId)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllOrdersByCarId(Long carId) {
        return orderRepository.findAllByCar(carId)
                .stream()
                .map(OrderMapper::fromJpa)
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersByCarId(Long carId) {
        return orderCustomRepository.findAllByCar(carId)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersByCarNumber(String carNumber) {
        return orderCustomRepository.findAllByCarNumber(carNumber)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllOrdersByStatus(String status) {
        return orderRepository.findAllByStatus(status)
                .stream()
                .map(OrderMapper::fromJpa)
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersByStatus(String status) {
        return orderCustomRepository.findAllByStatus(status)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllOrdersBetweenDates(Instant first, Instant second) {
        return orderRepository.findAllBetweenDates(first, second)
                .stream()
                .map(OrderMapper::fromJpa)
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersBetweenDates(Instant first, Instant second) {
        return orderCustomRepository.findAllBetweenDates(first, second)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersByDate(Instant orderDate) {
        return orderCustomRepository.findAllByDate(orderDate)
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllOrdersWithAccidents() {
        return orderRepository.findAllWithAccidents()
                .stream()
                .map(OrderMapper::fromJpa)
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public List<Order> findAllCustomOrdersWithAccidents() {
        return orderCustomRepository.findAllWithAccidents()
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    private CarJpa ensureCarExistsById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car with id %s does not exist.", id)));
    }

    private OrderJpa ensureOrderExistsById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id %s does not exist.", id)));
    }

    private CarRentalTimeJpa ensureCarRentalTimeExistsByOrderId(Long orderId) {
        return carRentalTimeRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException(String.format("CarRentalTime entity with order id %s does not exist.", orderId)));
    }

    private UserJpa ensureUserExistsById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
    }

    private int calculateNumberOfDays(CarRentalTimeJpa carRentalTimeJpa) {
        LocalDate start = LocalDate.ofInstant(carRentalTimeJpa.getStartRentalDate(), ZoneOffset.UTC);
        LocalDate end = LocalDate.ofInstant(carRentalTimeJpa.getEndRentalDate(), ZoneOffset.UTC);
        Period period = Period.between(end, start);
        return Math.abs(period.getDays());
    }

    private BigDecimal calculateInsuranceSum(int days, BigDecimal rentCarPricePerDay) {
        int insurance = rentCarPricePerDay.intValue() * days * INSURANCE_PERCENT / 100;
        return new BigDecimal(insurance);
    }

    private BigDecimal calculateOrderSumWithInsurance(int days, BigDecimal rentCarPricePerDay, BigDecimal insuranceSum) {
        return new BigDecimal(rentCarPricePerDay.intValue() * days + insuranceSum.intValue());
    }

    private BigDecimal calculateOrderSumWithoutInsurance(int days, BigDecimal rentCarPricePerDay) {
        return new BigDecimal(rentCarPricePerDay.intValue() * days);
    }

    private boolean ensureCarAvailable(CarRentalTimeJpa carRentalTimeJpa, CarJpa carJpa) {
        return carRepository.isCarAvailable(carJpa.getId(), carRentalTimeJpa.getStartRentalDate(), carRentalTimeJpa.getEndRentalDate());
    }

    private boolean ensureCarAvailableByOrderId(Long orderId, CarRentalTimeJpa carRentalTimeJpa, CarJpa carJpa) {
        return carRepository.isCarAvailableByOrderId(orderId, carJpa.getId(), carRentalTimeJpa.getStartRentalDate(), carRentalTimeJpa.getEndRentalDate());
    }

    private boolean isRentalDateChanged(CarRentalTimeJpa carRentalTimeJpaToUpdate, CarRentalTimeJpa existingCarRentalTime) {
        return carRentalTimeJpaToUpdate.getStartRentalDate() != existingCarRentalTime.getStartRentalDate() ||
                carRentalTimeJpaToUpdate.getEndRentalDate() != existingCarRentalTime.getEndRentalDate();
    }

    private Optional<BigDecimal> findPriceByOrder(Order order) {
        return carRepository.findById(order.getCar().getId())
                .map(c -> modelRepository.findById(c.getModelId()))
                .map(m -> categoryRepository.findById(m.get().getCategoryId()))
                .map(ct -> priceRepository.findById(ct.get().getPriceId()))
                .map(pr -> pr.get().getSum());
    }

    private Optional<BigDecimal> findPriceByOrderJpa(OrderJpa order) {
        return carRepository.findById(order.getCarId())
                .map(c -> modelRepository.findById(c.getModelId()))
                .map(m -> categoryRepository.findById(m.get().getCategoryId()))
                .map(ct -> priceRepository.findById(ct.get().getPriceId()))
                .map(pr -> pr.get().getSum());
    }
}