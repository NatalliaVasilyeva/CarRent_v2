package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.AccidentMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.AccidentJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.OrderJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.AccidentCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.AccidentRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.BrandRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CarRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.ModelRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.OrderRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserDetailsRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserRepository;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.service.exception.NotFoundException;
import com.dmdev.natalliavasilyeva.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccidentService {

    private static final Logger logger = LoggerFactory.getLogger(AccidentService.class);

    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();

    AccidentRepository accidentRepository = repositoryFactory.getAccidentRepository();
    AccidentCustomRepository accidentCustomRepository = repositoryCustomFactory.getAccidentCustomRepository();

    OrderRepository orderRepository = repositoryFactory.getOrderRepository();
    CarRepository carRepository = repositoryFactory.getCarRepository();
    UserRepository userRepository = repositoryFactory.getUserRepository();
    UserDetailsRepository userDetailsRepository = repositoryFactory.getUserDetailsRepository();
    ModelRepository modelRepository = repositoryFactory.getModelRepository();
    BrandRepository brandRepository = repositoryFactory.getBrandRepository();

    public Accident createAccident(Accident accident) {
            ensureOrderExistsById(accident.getOrderNumber());
            var savedAccident = accidentRepository.save(AccidentMapper.toAccidentJpa(accident))
                    .orElseThrow(() -> new ServiceException("Problem with accident saving", ErrorCode.INTERNAL_SERVER_ERROR));
            return createAccidentFromJpas(savedAccident);
    }

    public Accident updateAccident(Long id, Accident accident) {
            var existingAccident = ensureAccidentExistsById(id);
            ensureOrderExistsById(accident.getOrderNumber());

            existingAccident.setDate(accident.getDate());
            existingAccident.setDescription(accident.getDescription());
            existingAccident.setDamage(accident.getDamage());

            var updatedAccident = accidentRepository.update(existingAccident);

            return updatedAccident.map(this::createAccidentFromJpas)
                    .orElseThrow(() -> new ServiceException("Problem with accident saving", ErrorCode.INTERNAL_SERVER_ERROR));
    }

    public Accident getAccidentById(Long id) {

            var accidentJpa = ensureAccidentExistsById(id);
            var orderJpa = ensureOrderExistsById(accidentJpa.getOrderId());
            var userJpa = ensureUserExistsById(orderJpa.getUserId());
            var carJpa = ensureCarExistsById(orderJpa.getCarId());
            var modelJpa = modelRepository.findById(carJpa.getModelId())
                    .orElseThrow(() -> new NotFoundException(String.format("Model with id %s does not exist.", carJpa.getModelId()), ErrorCode.BAD_REQUEST));
            var brandJpa = brandRepository.findById(modelJpa.getBrandId())
                    .orElseThrow(() -> new NotFoundException(String.format("Brand with id %s does not exist.", modelJpa.getBrandId()), ErrorCode.BAD_REQUEST));
            var userDetailsJpa = userDetailsRepository.findByUserId(userJpa.getId())
                    .orElseThrow(() -> new NotFoundException(String.format("User details with with user id %s does not exist.", userJpa.getId()), ErrorCode.BAD_REQUEST));
            return AccidentMapper.fromJpa(accidentJpa, brandJpa, modelJpa, carJpa, userDetailsJpa);

    }

    public Accident getCustomAccidentById(Long id) {
        return accidentCustomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Accident with id %s does not exist.", id), ErrorCode.BAD_REQUEST));
    }

    public List<Accident> getAllAccidents() {
        List<AccidentJpa> accidentJpaList = accidentRepository.findAll();

        return AccidentMapper.fromJpaList(accidentJpaList)
                .stream()
                .sorted(Comparator.comparing(Accident::getDate))
                .collect(Collectors.toList());
    }

    public List<Accident> getAllCustomAccidents() {
        return accidentCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Accident::getDate))
                .collect(Collectors.toList());
    }

    public boolean deleteAccidentById(Long id) {
        ensureAccidentExistsById(id);
        return accidentRepository.deleteById(id);
    }

    public Accident deleteAccident(Accident accident) {
        ensureAccidentExistsById(accident.getId());
        Optional<AccidentJpa> accidentResult = accidentRepository.delete(AccidentMapper.toAccidentJpa(accident));

        return accidentResult.map(this::createAccidentFromJpas)
                .orElseThrow(() -> new ServiceException("Problem with accident deleting", ErrorCode.INTERNAL_SERVER_ERROR));
    }


    public List<Accident> findAllCustomAccidentsByDate(Instant date) {
        return accidentCustomRepository.findByDate(date)
                .stream()
                .sorted(Comparator.comparing(Accident::getDamage))
                .collect(Collectors.toList());
    }

    public List<Accident> findAllCustomAccidentsByDates(Instant first, Instant second) {
        return accidentCustomRepository.findByDates(first, second)
                .stream()
                .sorted(Comparator.comparing(Accident::getDamage))
                .collect(Collectors.toList());
    }

    public List<Accident> findAllCustomAccidentsByOrderId(Long orderId) {
        return accidentCustomRepository.findByOrderId(orderId)
                .stream()
                .sorted(Comparator.comparing(Accident::getDate))
                .collect(Collectors.toList());
    }

    public List<Accident> findAllCustomAccidentsByNameAndSurname(String username, String surname) {
        return accidentCustomRepository.findByUsernameAndSurname(username, surname)
                .stream()
                .sorted(Comparator.comparing(Accident::getDate))
                .collect(Collectors.toList());
    }

    public List<Accident> findAllCustomAccidentsByCarNumbers(List<String> carNumbers) {
        return accidentCustomRepository.findByCarNumbers(carNumbers)
                .stream()
                .sorted(Comparator.comparing(Accident::getDate))
                .collect(Collectors.toList());
    }

    public List<Accident> findAllCustomAccidentsByDamageMore(BigDecimal minDamage) {
        return accidentCustomRepository.findByDamage(minDamage)
                .stream()
                .sorted(Comparator.comparing(Accident::getDate))
                .collect(Collectors.toList());
    }

    private OrderJpa ensureOrderExistsById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id %s does not exist.", id), ErrorCode.BAD_REQUEST));
    }

    private AccidentJpa ensureAccidentExistsById(Long id) {
        return accidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Accident with id %s does not exist.", id), ErrorCode.BAD_REQUEST));
    }

    private UserJpa ensureUserExistsById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id), ErrorCode.BAD_REQUEST));
    }

    private CarJpa ensureCarExistsById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car with id %s does not exist.", id), ErrorCode.BAD_REQUEST));
    }

    private Accident createAccidentFromJpas(AccidentJpa accidentJpa) {
        var orderJpa = orderRepository.findById(accidentJpa.getOrderId());
        Optional<UserJpa> userJpa = Optional.empty();
        Optional<CarJpa> carJpa = Optional.empty();
        if (orderJpa.isPresent()) {
            userJpa = userRepository.findById(orderJpa.get().getUserId());
            carJpa = carRepository.findById(orderJpa.get().getCarId());
        }
        Optional<UserDetailsJpa> userDetailsJpa = Optional.empty();
        if (userJpa.isPresent()) {
            userDetailsJpa = userDetailsRepository.findByUserId(userJpa.get().getId());
        }
        Optional<ModelJpa> modelJpa = Optional.empty();
        if (carJpa.isPresent()) {
            modelJpa = modelRepository.findById(carJpa.get().getModelId());
        }
        Optional<BrandJpa> brandJpa = Optional.empty();
        if (modelJpa.isPresent()) {
            brandJpa = brandRepository.findById(modelJpa.get().getBrandId());
        }

        if (userDetailsJpa.isPresent() && brandJpa.isPresent()) {
            return AccidentMapper.fromJpa(accidentJpa, brandJpa.get(), modelJpa.get(), carJpa.get(), userDetailsJpa.get());
        } else {
            throw new CustomException("Problem with accident creating", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}