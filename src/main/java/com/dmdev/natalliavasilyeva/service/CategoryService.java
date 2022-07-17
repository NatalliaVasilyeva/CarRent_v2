package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.CategoryMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.CategoryJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.PriceJpa;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.persistence.exception.BrandBadRequestException;
import com.dmdev.natalliavasilyeva.persistence.exception.NotFoundException;
import com.dmdev.natalliavasilyeva.persistence.exception.PriceBadRequestException;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.CategoryCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.CategoryRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();

    CategoryRepository categoryRepository = repositoryFactory.getCategoryRepository();
    PriceRepository priceRepository = repositoryFactory.getPriceRepository();
    CategoryCustomRepository categoryCustomRepository = repositoryCustomFactory.getCategoryCustomRepository();


    public Category createCategory(Category category) {
        isUniqueCategoryName(category.getName());
        ensurePriceExistsById(category.getPrice().getId());
        var jpa = CategoryMapper.toJpa(category);
        return categoryRepository.save(jpa)
                .map(CategoryMapper::fromJpa)
                .orElseThrow(RuntimeException::new);
    }

    public Category updateCategory(Long id, Category category) {
        var existingCategory = ensureCategoryExistsById(id);
        if (!existingCategory.getName().equals(category.getName())) {
            isUniqueCategoryName(category.getName());
        }
        existingCategory.setName(category.getName());
        existingCategory.setPriceId(category.getPrice().getId());
        return categoryRepository.update(existingCategory)
                .map(CategoryMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with category updating"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public List<Category> getAllCustomCategories() {
        return categoryCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getName))
                .collect(Collectors.toList());
    }

    public Category getCustomCategoryById(Long id) {
        ensureCategoryExistsById(id);
        return categoryCustomRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Problem with getting custom category with id %s", id)));
    }

    public boolean deleteCategoryById(Long id) {
        ensureCategoryExistsById(id);
        return categoryRepository.deleteById(id);
    }

    public Category deleteCategory(Category category) {
        ensureCategoryExistsById(category.getId());
        return categoryRepository.delete(CategoryMapper.toJpa(category))
                .map(CategoryMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with category deleting"));
    }

    public Category findCategoryByName(String name) {
        ensureCategoryExistsByName(name);
        return categoryRepository.findByName(name)
                .map(CategoryMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with category finding"));
    }

    public List<Category> findCategoryByNames(List<String> categoryNames) {
        List<String> existsCategoryNames = categoryNames.stream().filter(brand -> categoryRepository.existByName(brand)).collect(Collectors.toList());
        return categoryRepository.findByNames(existsCategoryNames)
                .stream()
                .map(CategoryMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public Category findCustomCategoryByName(String name) {
        ensureCategoryExistsByName(name);
        return categoryCustomRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(String.format("Category with name '%s' doesn't exists", name)));
    }

    public List<Category> findCustomCategoryByPrice(String sign, BigDecimal price) {
        ensurePriceExistsBySum(price);
        return categoryCustomRepository.findByPrice(sign, price);
    }

    private CategoryJpa ensureCategoryExistsById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s does not exist.", id)));
    }

    private PriceJpa ensurePriceExistsById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Price with id %s does not exist.", id)));
    }

    private void ensurePriceExistsBySum(BigDecimal sum) {
        if (!priceRepository.existByPriceSum(sum)) {
            throw new PriceBadRequestException(String.format("Price with sum %d doesn't exist.", sum));
        }
    }

    private void ensureCategoryExistsByName(String name) {
        if (!categoryRepository.existByName(name)) {
            throw new NotFoundException(String.format("Category with name %s does not exist.", name));
        }
    }

    private void isUniqueCategoryName(String name) {
        if (categoryRepository.existByName(name)) {
            throw new BrandBadRequestException(String.format("Category with name '%s' already exists", name));
        }
    }
}