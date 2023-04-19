package org.olmedo.commands.jdbc.service;


import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.repositorio.CategoryRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.CommandRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.Repositorio;
import org.olmedo.commands.jdbc.utils.ConexionBaseDeDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogueService implements Service {

    private Repositorio<Command> commandRepository;
    private Repositorio<Category> categoryRespository;

    public CatalogueService() {
        this.commandRepository = new CommandRepositoryImpl();
        this.categoryRespository = new CategoryRepositoryImpl();
    }

    @Override
    public List<Command> list() throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            commandRepository.setConn(conn);

            return commandRepository.findAll();
        }
    }

    @Override
    public Command byId(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            commandRepository.setConn(conn);

            return commandRepository.byId(id);
        }
    }

    @Override
    public Command save(Command command) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            commandRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            Command newCommand = null;

            try  {

                newCommand = commandRepository.save(command);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

            return newCommand;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            commandRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {

                commandRepository.delete(id);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        }

    }

    @Override
    public List<Category> listCategory() throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            categoryRespository.setConn(conn);

            return categoryRespository.findAll();
        }
    }

    @Override
    public Category byIdCategory(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            categoryRespository.setConn(conn);

            return categoryRespository.byId(id);
        }
    }

    @Override
    public Category saveCategory(Category category) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            categoryRespository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            Category newCategory = null;

            try {

                newCategory = categoryRespository.save(category);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

            return newCategory;
        }
    }

    @Override
    public void deleteCategory(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            categoryRespository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {

                categoryRespository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        }

    }

    @Override
    public void saveCommandCategory(Command command, Category category) throws SQLException {
        try (Connection conn = ConexionBaseDeDatos.getConnection()) {
            commandRepository.setConn(conn);
            categoryRespository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {

                Category newCategory = categoryRespository.save(category);
                command.setCategory(newCategory);

                commandRepository.save(command);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        }

    }
}
