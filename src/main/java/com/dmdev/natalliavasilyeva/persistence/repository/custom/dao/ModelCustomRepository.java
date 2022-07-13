package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ModelResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ModelCustomRepository extends AbstractCustomRepository<Model> implements GenericCustomRepository<Model, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ModelCustomRepository.class);
    ResultSetExtractor<Model> extractor;

    public ModelCustomRepository() {
        this.extractor = new ModelResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT m.id                                as model_id,\n" +
            "       m.name                              as model_name,\n" +
            "       m.transmission                      as model_transmission,\n" +
            "       m.engine_type                       as model_engine_type,\n" +
            "       row_to_json(brand)                  as brand_description,\n" +
            "       row_to_json(category)               as category_description\n" +
            "FROM model m\n" +
            "       LEFT JOIN (\n" +
            "               SELECT b.id                 as brand_id,\n" +
            "                      b.name               as brand_name\n" +
            "               FROM brand b) AS brand" +
            "       ON m.brand_id = brand.brand_id\n" +
            "       LEFT JOIN (\n" +
            "               SELECT c.id                 as category_id,\n" +
            "                      c.name               as category_name,\n" +
            "                      row_to_json(prices)  as price_description\n" +
            "               FROM categories c\n" +
            "                      LEFT JOIN (\n" +
            "                           SELECT p.id,\n" +
            "                                  p.price  as sum\n" +
            "                           FROM price p)   as prices\n " +
            "                      ON c.price_id = prices.id)\n" +
            "AS category ON m.category_id = category.category_id\n";


    @Override
    public Optional<Model> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE m.id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<Model> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX);
        return findAll(statementProvider, extractor);
    }

    public List<Model> findAllByBrandIds(List<Long> brandIds) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE brand.brand_id = ANY (?)", brandIds.toArray(new Long[0]));
        return findAll(statementProvider, extractor);
    }

    public List<Model> findAllByBrandName(String brandName) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE brand.brand_name LIKE ?", brandName);
        return findAll(statementProvider, extractor);
    }

    public List<Model> findAllByTransmission(String transmission) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE m.transmission LIKE ?", transmission);
        return findAll(statementProvider, extractor);
    }

    public List<Model> findAllByEngineType(String engineType) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE m.engine_type LIKE ?", engineType);
        return findAll(statementProvider, extractor);
    }

    public List<Model> findAllByCategory(String category) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE category_name LIKE ?", category);
        return findAll(statementProvider, extractor);
    }
}