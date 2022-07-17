package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.AccidentJpaRepository;
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

public class ServiceFactory {

    private final static ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private BrandService brandService;
    private CarService carService;
    private CategoryService categoryService;
    private FilesService filesService;
    private ModelService modelService;
    private PriceService priceService;
//    private UserService userService;

    public BrandService getBrandService() {
        return brandService != null ? brandService : new BrandService();
    }

    public CarService getCarService() {
        return carService != null ? carService : new CarService();
    }

    public CategoryService getCategoryService() {
        return categoryService != null ? categoryService : new CategoryService();
    }

    public FilesService getFilesService() {
        return filesService != null ? filesService : new FilesService();
    }

    public ModelService getModelService() {
        return modelService != null ? modelService : new ModelService();
    }

    public PriceService getPriceService() {
        return priceService != null ? priceService : new PriceService();
    }

//    public UserService getUserService() {
//        return userService != null ? userService : new UserService();
//    }

}