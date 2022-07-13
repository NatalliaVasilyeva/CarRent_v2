package com.dmdev.natalliavasilyeva.persistence.repository.custom;

import com.dmdev.natalliavasilyeva.domain.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface GenericCustomRepository<T extends Identifiable, K> {
    List<T> findAll();

    Optional<T> findById(K id);
}