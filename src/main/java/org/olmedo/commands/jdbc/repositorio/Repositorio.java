package org.olmedo.commands.jdbc.repositorio;

import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {

    List<T> findAll() throws SQLException;
    T byId(Long id) throws SQLException;
    T save(T t) throws SQLException;
    void delete(Long id) throws SQLException;
}
