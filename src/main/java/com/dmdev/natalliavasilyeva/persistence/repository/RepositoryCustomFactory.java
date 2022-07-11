package com.dmdev.natalliavasilyeva.persistence.repository;


import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.AccidentCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.BrandCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.CarCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.CategoryCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.ModelCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.OrderCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.UserCustomRepository;

public class RepositoryCustomFactory {

    private final static RepositoryCustomFactory INSTANCE = new RepositoryCustomFactory();

    private RepositoryCustomFactory() {
    }

    public static RepositoryCustomFactory getInstance() {
        return INSTANCE;
    }

    private AccidentCustomRepository accidentCustomRepository;
    private BrandCustomRepository brandCustomRepository;
    private CarCustomRepository carCustomRepository;
    private CategoryCustomRepository categoryCustomRepository;
    private ModelCustomRepository modelCustomRepository;
    private OrderCustomRepository orderCustomRepository;
    private UserCustomRepository userCustomRepository;


    public AccidentCustomRepository getAccidentCustomRepository() {
        return accidentCustomRepository != null ? accidentCustomRepository : (accidentCustomRepository = new AccidentCustomRepository());
    }

    public BrandCustomRepository getBrandCustomRepository() {
        return brandCustomRepository != null ? brandCustomRepository : (brandCustomRepository = new BrandCustomRepository());
    }

    public CarCustomRepository getCarCustomRepository() {
        return carCustomRepository != null ? carCustomRepository : (carCustomRepository = new CarCustomRepository());
    }

    public CategoryCustomRepository getCategoryCustomRepository() {
        return categoryCustomRepository != null ? categoryCustomRepository : (categoryCustomRepository = new CategoryCustomRepository());
    }

    public ModelCustomRepository getModelCustomRepository() {
        return modelCustomRepository != null ? modelCustomRepository : (modelCustomRepository = new ModelCustomRepository());
    }

    public OrderCustomRepository getOrderCustomRepository() {
        return orderCustomRepository != null ? orderCustomRepository : (orderCustomRepository = new OrderCustomRepository());
    }

    public UserCustomRepository getUserCustomRepository() {
        return userCustomRepository != null ? userCustomRepository : (userCustomRepository = new UserCustomRepository());
    }
}