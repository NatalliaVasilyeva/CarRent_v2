package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.BrandMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.BrandCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.BrandRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CategoryRepository;
import com.dmdev.natalliavasilyeva.service.exception.BrandBadRequestException;
import com.dmdev.natalliavasilyeva.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();
    BrandRepository brandRepository = repositoryFactory.getBrandRepository();

    CategoryRepository categoryRepository = repositoryFactory.getCategoryRepository();
    BrandCustomRepository brandCustomRepository = repositoryCustomFactory.getBrandCustomRepository();


    public Brand createBrand(Brand brand) {
        isUniqueBrandName(brand.getName());
        var jpa = BrandMapper.toJpa(brand);
        return brandRepository.save(jpa)
                .map(BrandMapper::fromJpa)
                .orElseThrow(RuntimeException::new);
    }

    public Brand updateBrand(Long id, Brand brand) {
        var existingBrand = ensureBrandExistsById(id);
        if (!existingBrand.getName().equals(brand.getName())) {
            isUniqueBrandName(brand.getName());
        }
        existingBrand.setName(brand.getName());
        return brandRepository.update(existingBrand)
                .map(BrandMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with brand updating"));
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public List<Brand> getAllCustomBrands() {
        return brandCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Brand::getName))
                .collect(Collectors.toList());
    }

    public Brand getCustomBrandById(Long id) {
        ensureBrandExistsById(id);
        return brandCustomRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Problem with getting custom brand with id %s", id)));
    }

    public boolean deleteBrandById(Long id) {
        ensureBrandExistsById(id);
        return brandRepository.deleteById(id);
    }

    public Brand deleteBrand(Brand brand) {
        ensureBrandExistsById(brand.getId());
        return brandRepository.delete(BrandMapper.toJpa(brand))
                .map(BrandMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with brand deleting"));
    }

    public Brand findBrandByName(String name) {

        return brandRepository.findByName(name)
                .map(BrandMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException(String.format("Brand with name '%s' doesn't exists", name)));
    }

    public Brand findCustomBrandByName(String name) {

        return brandCustomRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(String.format("Brand with name '%s' doesn't exists", name)));
    }

    public List<Brand> findBrandByNames(List<String> brandNames) {
        List<String> existsBrandNames = brandNames.stream().filter(brand -> brandRepository.existByName(brand)).collect(Collectors.toList());
        return brandRepository.findByNames(existsBrandNames)
                .stream()
                .map(BrandMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public List<Brand> findCustomBrandsByCategory(String category) {
        ensureCategoryExistsByName(category);
        return brandCustomRepository.findByCategory(category);
    }

    private BrandJpa ensureBrandExistsById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id %s does not exist.", id)));
    }

    private void ensureCategoryExistsByName(String name) {
        if (!categoryRepository.existByName(name)) {
            throw new NotFoundException(String.format("Category with name %s does not exist.", name));
        }
    }

    private void isUniqueBrandName(String name) {
        if (brandRepository.existByName(name)) {
            throw new BrandBadRequestException(String.format("Brand with name '%s' already exists", name));
        }
    }
}