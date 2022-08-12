package com.dmdev.natalliavasilyeva.service;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private BrandService brandService;
    private AccidentService accidentService;
    private CarService carService;
    private CategoryService categoryService;
    private FilesService filesService;
    private ModelService modelService;
    private PriceService priceService;
    private DriverLicenseService driverLicenseService;
    private OrderService orderService;
    private UserService userService;

    public BrandService getBrandService() {
        return brandService != null ? brandService : new BrandService();
    }

    public AccidentService getAccidentService() {
        return accidentService != null ? accidentService : new AccidentService();
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
    public DriverLicenseService getDriverLicenseService() {
        return driverLicenseService != null ? driverLicenseService : new DriverLicenseService();
    }
    public OrderService getOrderService() {
        return orderService != null ? orderService : new OrderService();
    }

    public UserService getUserService() {
        return userService != null ? userService : new UserService();
    }

}