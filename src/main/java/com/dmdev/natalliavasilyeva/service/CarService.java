package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CategoryJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.persistence.exception.NotFoundException;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.CarCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.BrandRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CarRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CategoryRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();

    ModelRepository modelRepository = repositoryFactory.getModelRepository();
    BrandRepository brandRepository = repositoryFactory.getBrandRepository();
    CategoryRepository categoryRepository = repositoryFactory.getCategoryRepository();
    CarRepository carRepository = repositoryFactory.getCarRepository();
    CarCustomRepository carCustomRepository = repositoryCustomFactory.getCarCustomRepository();
    FilesService carImageService = new FilesService();


    public Car createCar(Car car) {
        Long brandId = null;
        Long modelId = null;
        var transmission = car.getModel().getTransmission();
        var engine = car.getModel().getEngineType();

        var models = findModelByNameTransmissionEngine(car.getModel().getName(), transmission.name(), engine.name());
        if (models.isEmpty()) {
            var brandName = car.getModel().getBrand().getName();
            var modelName = car.getModel().getName();
            var brand = findBrandByName(brandName);
            if (brand == null) {
                BrandJpa savedBrand = brandRepository.save(new BrandJpa.Builder().name(brandName).build())
                        .orElseThrow(() -> new RuntimeException("Problem with brand saving"));
                brandId = savedBrand.getId();
            } else {
                brandId = brand.getId();
            }
            Optional<CategoryJpa> optional = categoryRepository.findByName("ECONOMY");
            if (optional.isEmpty()) {
                throw new RuntimeException("Problem with model saving");
            }
            CategoryJpa categoryJpa = optional.get();

            ModelJpa savedModelJpa = modelRepository.save(new ModelJpa.Builder()
                    .brand(brandId)
                    .category(categoryJpa.getId())
                    .name(modelName)
                    .transmission(transmission)
                    .engine(engine)
                    .build()
            ).orElseThrow(() -> new RuntimeException("Problem with model saving"));

            modelId = savedModelJpa.getId();

            CarJpa savedCar = carRepository.save(new CarJpa.Builder()
                            .model(modelId)
                            .color(car.getColor())
                            .year(car.getYearOfProduction())
                            .number(car.getNumber())
                            .vin(car.getVin())
                            .repaired(car.isRepaired())
                            .image(car.getImage())
                            .build())
                    .orElseThrow(() -> new RuntimeException("Problem with car saving"));

            carImageService.downloadImage(car.getImage(), car.getImageContent());

            return CarMapper.fromJpa(savedCar);
        } else {
            var brand = findBrandByName(car.getModel().getBrand().getName());
            List<ModelJpa> filteredModels = models.stream()
                    .filter(modelJpa -> modelJpa.getBrandId() == brand.getId())
                    .collect(Collectors.toList());
            CarJpa savedCar = carRepository.save(new CarJpa.Builder()
                            .model(filteredModels.get(0).getId())
                            .color(car.getColor())
                            .year(car.getYearOfProduction())
                            .number(car.getNumber())
                            .vin(car.getVin())
                            .repaired(car.isRepaired())
                            .image(car.getImage())
                            .build())
                    .orElseThrow(() -> new RuntimeException("Problem with car saving"));

            carImageService.downloadImage(car.getImage(), car.getImageContent());

            return CarMapper.fromJpa(savedCar);
        }
    }

    public Car updateCar(Long id, Car car) {
        var existingCar = ensureCarExistsById(id);
        ensureModelExistsById(car.getModel().getId());

        existingCar.setModelId(car.getModel().getId());
        existingCar.setColor(car.getColor());
        existingCar.setYearOfProduction(car.getYearOfProduction());
        existingCar.setNumber(car.getNumber());
        existingCar.setVin(car.getVin());
        existingCar.setRepaired(car.isRepaired());

        if (car.getImage() != null && car.getImageContent() != null) {
            existingCar.setImage(car.getImage());
            carImageService.downloadImage(car.getImage(), car.getImageContent());
        } else if (car.getImage() != null) {
            existingCar.setImage(car.getImage());
        }

        return carRepository.update(existingCar)
                .map(CarMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with car updating"));
    }

    public Car getCarById(Long id) {
        return CarMapper.fromJpa(ensureCarExistsById(id));
    }

    public Car getCustomCarById(Long id) {
        return carCustomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car with id %s does not exist.", id)));
    }

    public List<Car> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarMapper::fromJpa)
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> getAllCustomCars() {
        return carCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public boolean deleteCarById(Long id) {
        ensureCarExistsById(id);
        return carRepository.deleteById(id);
    }

    public Car deleteCar(Car car) {
        ensureCarExistsById(car.getId());
        return carRepository.delete(CarMapper.toJpa(car))
                .map(CarMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with car deleting"));
    }

    public List<Car> findAllCarsUnderRepair() {
        return carRepository.findAllUnderRepair()
                .stream()
                .map(CarMapper::fromJpa)
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCarsByBrandName(String brandName) {
        ensureBrandExistsByName(brandName);
        return carRepository.findAllByBrandName(brandName)
                .stream()
                .map(CarMapper::fromJpa)
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsByBrandName(String brandName) {
        ensureBrandExistsByName(brandName);
        return carCustomRepository.findAllByBrandName(brandName)
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCarsByBrandId(Long brandId) {
        ensureBrandExistsById(brandId);
        return carRepository.findAllByBrandId(brandId)
                .stream()
                .map(CarMapper::fromJpa)
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsByBrandId(Long brandId) {
        ensureBrandExistsById(brandId);
        return carCustomRepository.findAllByBrandId(brandId)
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCarsByCategoryName(String categoryName) {
        ensureCategoryExistsByName(categoryName);
        return carRepository.findAllByCategoryName(categoryName)
                .stream()
                .map(CarMapper::fromJpa)
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsByCategoryName(String categoryName) {
        ensureCategoryExistsByName(categoryName);
        return carCustomRepository.findAllByCategoryName(categoryName)
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsByYear(String year) {
        return carCustomRepository.findAllByYear(year)
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsBetweenYears(String firstYear, String secondYear) {
        return carCustomRepository.findAllBetweenYears(firstYear, secondYear)
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsWithAccidents() {
        return carCustomRepository.findAllWithAccidents()
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsWithoutAccidents() {
        return carCustomRepository.findAllWithoutAccidents()
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsUnderRepaired() {
        return carCustomRepository.findAllUnderRepair()
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCustomCarsAvailable() {
        return carCustomRepository.findAllAvailable()
                .stream()
                .sorted(Comparator.comparing(Car::getYearOfProduction))
                .collect(Collectors.toList());
    }

    public Car findCarByNumber(String carNumber) {
        return carRepository.findByCarNumber(carNumber)
                .map(CarMapper::fromJpa)
                .orElseThrow(() -> new NotFoundException(String.format("Car with number %s does not exist.", carNumber)));
    }

    public Car findCustomCarByNumber(String carNumber) {
        return carCustomRepository.findByCarNumber(carNumber)
                .orElseThrow(() -> new NotFoundException(String.format("Car with number %s does not exist.", carNumber)));
    }

    public boolean isCarAvailable(Long carId, Instant startDate, Instant endDate) {
        ensureCarExistsById(carId);
        return carRepository.isCarAvailable(carId, startDate, endDate);
    }

    public boolean isCustomCarAvailable(Long carId, Instant startDate, Instant endDate) {
        ensureCarExistsById(carId);
        return carCustomRepository.isCarAvailable(carId, startDate, endDate);
    }

    private CarJpa ensureCarExistsById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car with id %s does not exist.", id)));
    }

    private void ensureBrandExistsById(Long id) {
        if (!brandRepository.existById(id)) {
            throw new NotFoundException(String.format("Brand with id %s does not exist.", id));
        }
    }

    private ModelJpa ensureModelExistsById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Model with id %s does not exist.", id)));
    }

    private void ensureCategoryExistsByName(String name) {
        if (!categoryRepository.existByName(name)) {
            throw new NotFoundException(String.format("Category with name %s does not exist.", name));
        }
    }

    private void ensureBrandExistsByName(String name) {
        if (!brandRepository.existByName(name)) {
            throw new NotFoundException(String.format("Brand with name %s does not exist.", name));
        }
    }

    private BrandJpa findBrandByName(String name) {
        return brandRepository.findByName(name).orElse(null);
    }

    private List<ModelJpa> findModelByNameTransmissionEngine(String name, String transmission, String engineType) {
        return modelRepository.findByNameTransmissionEngine(name, transmission, engineType);
    }
}