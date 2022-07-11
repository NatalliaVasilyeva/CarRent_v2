package com.dmdev.natalliavasilyeva.persistence.repository;

import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.AccidentRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.BrandRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CarRentalTimeRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CarRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CategoryRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.DriverLicenseRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.ModelRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.OrderRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.PriceRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserDetailsRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserRepository;

public class RepositoryFactory {

    private final static RepositoryFactory INSTANCE = new RepositoryFactory();

    private RepositoryFactory() {
    }

    public static RepositoryFactory getInstance() {
        return INSTANCE;
    }

    private AccidentRepository accidentRepository;
    private BrandRepository brandRepository;
    private CarRentalTimeRepository carRentalTimeRepository;
    private CarRepository carRepository;
    private CategoryRepository categoryRepository;
    private DriverLicenseRepository driverLicenseRepository;
    private ModelRepository modelRepository;
    private OrderRepository orderRepository;
    private PriceRepository priceRepository;
    private UserDetailsRepository userDetailsRepository;
    private UserRepository userRepository;


    public AccidentRepository getAccidentRepository() {
        return accidentRepository != null ? accidentRepository : (accidentRepository = new AccidentRepository());
    }

    public BrandRepository getBrandRepository() {
        return brandRepository != null ? brandRepository : (brandRepository = new BrandRepository());
    }

    public CarRentalTimeRepository getCarRentalTimeRepository() {
        return carRentalTimeRepository != null ? carRentalTimeRepository : (carRentalTimeRepository = new CarRentalTimeRepository());
    }

    public CarRepository getCarRepository() {
        return carRepository != null ? carRepository : (carRepository = new CarRepository());
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository != null ? categoryRepository : (categoryRepository = new CategoryRepository());
    }

    public DriverLicenseRepository getDriverLicenseRepository() {
        return driverLicenseRepository != null ? driverLicenseRepository : (driverLicenseRepository = new DriverLicenseRepository());
    }

    public ModelRepository getModelRepository() {
        return modelRepository != null ? modelRepository : (modelRepository = new ModelRepository());
    }

    public OrderRepository getOrderRepository() {
        return orderRepository != null ? orderRepository : (orderRepository = new OrderRepository());
    }

    public PriceRepository getPriceRepository() {
        return priceRepository != null ? priceRepository : (priceRepository = new PriceRepository());
    }

    public UserDetailsRepository getUserDetailsRepository() {
        return userDetailsRepository != null ? userDetailsRepository : (userDetailsRepository = new UserDetailsRepository());
    }

    public UserRepository getUserRepository() {
        return userRepository != null ? userRepository : (userRepository = new UserRepository());
    }
}