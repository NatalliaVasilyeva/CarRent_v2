package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.PriceMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.PriceJpa;
import com.dmdev.natalliavasilyeva.domain.model.Price;
import com.dmdev.natalliavasilyeva.persistence.exception.NotFoundException;
import com.dmdev.natalliavasilyeva.persistence.exception.PriceBadRequestException;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PriceService {

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    PriceRepository priceRepository = repositoryFactory.getPriceRepository();


    public Price createPrice(Price price) {
        ensurePriceExistsBySum(price.getSum());
        var jpa = PriceMapper.toJpa(price);
        return priceRepository.save(jpa)
                .map(PriceMapper::fromJpa)
                .orElseThrow(RuntimeException::new);
    }

    public Price updatePrice(Long id, Price price) {
        var existingPrice = ensurePriceExistsById(id);
        if (!existingPrice.getSum().equals(price.getSum())) {
            ensurePriceExistsBySum(price.getSum());
        }
        existingPrice.setSum(price.getSum());
        return priceRepository.update(existingPrice)
                .map(PriceMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with price updating"));
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll()
                .stream()
                .map(PriceMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public boolean deletePriceById(Long id) {
        ensurePriceExistsById(id);
        return priceRepository.deleteById(id);
    }

    public Price deletePrice(Price price) {
        ensurePriceExistsById(price.getId());
        return priceRepository.delete(PriceMapper.toJpa(price))
                .map(PriceMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with price deleting"));
    }

    private PriceJpa ensurePriceExistsById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Price with id %s does not exist.", id)));
    }

    private void ensurePriceExistsBySum(BigDecimal sum) {
        if (priceRepository.existByPriceSum(sum)) {
            throw new PriceBadRequestException(String.format("Price with sum %d already exist.", sum));
        }
    }
}