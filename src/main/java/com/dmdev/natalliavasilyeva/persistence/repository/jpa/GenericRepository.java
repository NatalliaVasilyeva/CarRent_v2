package com.dmdev.natalliavasilyeva.persistence.repository.jpa;


import com.dmdev.natalliavasilyeva.domain.jpa.Entity;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T extends Entity, K> {
    List<T> findAll();

    Optional<T> findById(K id);

    boolean deleteById(K id);

    Optional<T> delete(T object);

    Optional<T> update(T object);

    Optional<T> save(T object);
}