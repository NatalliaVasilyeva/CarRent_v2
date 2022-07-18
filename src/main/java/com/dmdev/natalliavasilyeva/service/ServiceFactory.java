package com.dmdev.natalliavasilyeva.service;

public class ServiceFactory {

    private final static ServiceFactory INSTANCE = new ServiceFactory();

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