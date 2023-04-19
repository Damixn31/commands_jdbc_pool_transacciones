package org.olmedo.commands.jdbc.repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {

  // para acceder al metodo y asignar la coneccion tengo que pasarle a la interface la coneccion
 void setConn(Connection conn);

    List<T> findAll() throws SQLException;
    T byId(Long id) throws SQLException;
    T save(T t) throws SQLException;
    void delete(Long id) throws SQLException;
}
