package org.olmedo.commands.jdbc.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class ConexionBaseDeDatos {


    private static String url = "jdbc:mysql://localhost:3306/linux_commands?serverTimezone=America/Argentina/Buenos_Aires";
    private static String username = "root";
    private static String password = "9521";

    // en el pom.xml meti la dependecia de apache commonds para usar el pool de conexiones
    private static BasicDataSource pool;

    public static BasicDataSource getInstance() throws SQLException {
        if(pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);

            // configuracion del pool inicial aca tiene tres conexiones abiertas habilitadas
            pool.setInitialSize(3);
            // configuracion del minimo de conexion inactivas que estan esperando para ser usadas
            pool.setMinIdle(3);
            pool.setMaxIdle(8);

            // el total de activas que se esten usando y inactivas
            pool.setMaxTotal(8);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}

