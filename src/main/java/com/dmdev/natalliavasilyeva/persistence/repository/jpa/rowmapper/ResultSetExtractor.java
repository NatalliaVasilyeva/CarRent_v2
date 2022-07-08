package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.persistence.jpa.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T extends Entity> {

    T extractData(ResultSet rs) throws SQLException;
}