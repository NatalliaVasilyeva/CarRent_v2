package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.Entity;

import java.sql.ResultSet;

public interface ResultSetExtractor<T extends Entity> {
    T extractData(ResultSet rs);
}