package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.BrandResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BrandCustomRepository extends AbstractCustomRepository<Brand> implements GenericCustomRepository<Brand, Long> {

    private static final Logger logger = LoggerFactory.getLogger(BrandCustomRepository.class);
    ResultSetExtractor<Brand> extractor;

    public BrandCustomRepository() {
        this.extractor = new BrandResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT b.id                            as brand_id,\n" +
            "       b.name                          as brand_name,\n" +
            "       json_agg(row_to_json(mod))      as model_description\n" +
            "FROM brand b\n" +
            "       LEFT JOIN (\n" +
            "               SELECT m.id             as mod_id,\n" +
            "                      m.name           as model_name,\n" +
            "                      m.transmission   as transmission,\n" +
            "                      m.engine_type    as engine,\n" +
            "                      c.name           as category_name\n" +
            "               FROM model m\n" +
            "                       LEFT JOIN  categories c ON m.category_id = c.id\n" +
            "               ) AS mod ON b.id = mod.mod_id\n";

    private static final String GROUP_BY = "" +
            "GROUP BY b.id";
    @Override
    public List<Brand> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }
    @Override
    public Optional<Brand> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE b.id = ?\n", id)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public Optional<Brand> findByName(String name) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE b.name = ?\n", name)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public List<Brand> findByCategory(String category) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE category_name = ?\n", category)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }
}