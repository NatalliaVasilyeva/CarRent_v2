package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.ModelMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.ModelCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.BrandRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CategoryRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.ModelRepository;
import com.dmdev.natalliavasilyeva.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();

    ModelRepository modelRepository = repositoryFactory.getModelRepository();
    BrandRepository brandRepository = repositoryFactory.getBrandRepository();
    CategoryRepository categoryRepository = repositoryFactory.getCategoryRepository();
    ModelCustomRepository modelCustomRepository = repositoryCustomFactory.getModelCustomRepository();


    public Model createModel(Model model) {
        ensureBrandExistsById(model.getBrand().getId());
        ensureCategoryExistsById(model.getCategory().getId());
        List<ModelJpa> jpas = modelRepository.findByNameTransmissionEngine(model.getName(), model.getTransmission().name(), model.getEngineType().name());
        List<ModelJpa> filteredJpas = jpas.stream()
                .filter(modelJpa -> modelJpa.getCategoryId() == model.getCategory().getId() && modelJpa.getBrandId() == model.getBrand().getId())
                .collect(Collectors.toList());

        if (filteredJpas.isEmpty()) {
            var jpa = ModelMapper.toJpa(model);
            return modelRepository.save(jpa)
                    .map(ModelMapper::fromJpa)
                    .orElseThrow(RuntimeException::new);
        } else {
            throw new RuntimeException("Model with such data already exist");
        }
    }

    public Model updateModel(Long id, Model model) {
        var existingModel = ensureModelExistsById(id);
        ensureBrandExistsById(model.getBrand().getId());
        ensureCategoryExistsById(model.getCategory().getId());

        existingModel.setBrandId(model.getBrand().getId());
        existingModel.setCategoryId(model.getCategory().getId());
        existingModel.setName(model.getName());
        existingModel.setTransmission(model.getTransmission());
        existingModel.setEngineType(model.getEngineType());
        return modelRepository.update(existingModel)
                .map(ModelMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with model updating"));
    }

    public Model getModelById(Long id) {
        return ModelMapper.fromJpa(ensureModelExistsById(id));
    }

    public Model getCustomModelById(Long id) {
        return modelCustomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Model with id %s does not exist.", id)));
    }

    public List<Model> getAllModels() {
        return modelRepository.findAll()
                .stream()
                .map(ModelMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public List<Model> getAllCustomModels() {
        return modelCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    public boolean deleteModelById(Long id) {
        ensureModelExistsById(id);
        return modelRepository.deleteById(id);
    }

    public Model deleteModel(Model model) {
        ensureModelExistsById(model.getId());
        return modelRepository.delete(ModelMapper.toJpa(model))
                .map(ModelMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with model deleting"));
    }

    public List<Model> findCustomModelListByBrandIds(List<Long> brandIds) {
        List<Long> existsBrandIds = brandIds.stream()
                .filter(id -> modelRepository.findById(id).isPresent())
                .collect(Collectors.toList());
        if (existsBrandIds.isEmpty()) {
            throw new NotFoundException("Models with these ids does not exist.");
        }
        return modelCustomRepository.findAllByBrandIds(existsBrandIds)
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    public List<Model> findCustomModelListByBrandId(Long brandId) {
        ensureBrandExistsById(brandId);
        return modelCustomRepository.findAllByBrandIds(List.of(brandId))
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    public List<Model> findCustomModelListByBrandName(String brandName) {
        ensureBrandExistsByName(brandName);
        return modelCustomRepository.findAllByBrandName(brandName)
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    public List<Model> findCustomModelListByCategoryName(String categoryName) {
        ensureCategoryExistsByName(categoryName);
        return modelCustomRepository.findAllByCategory(categoryName)
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    public List<Model> findCustomModelListByTransmission(Transmission transmission) {
        return modelCustomRepository.findAllByTransmission(transmission.name())
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    public List<Model> findCustomModelListByEngineType(EngineType engineType) {
        return modelCustomRepository.findAllByEngineType(engineType.name())
                .stream()
                .sorted(Comparator.comparing(Model::getName))
                .collect(Collectors.toList());
    }

    private ModelJpa ensureModelExistsById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Model with id %s does not exist.", id)));
    }

    private void ensureBrandExistsById(Long id) {
        if (!brandRepository.existById(id)) {
            throw new NotFoundException(String.format("Brand with id %s does not exist.", id));
        }
    }

    private void ensureCategoryExistsById(Long id) {
        if (!categoryRepository.existById(id)) {
            throw new NotFoundException(String.format("Category with id %s does not exist.", id));
        }
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
}