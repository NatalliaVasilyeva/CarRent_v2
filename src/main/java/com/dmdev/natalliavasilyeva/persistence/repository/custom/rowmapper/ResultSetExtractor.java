package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T extends Identifiable> {

    T extractData(ResultSet rs) throws SQLException;

}