package org.olmedo.commands.jdbc.controllers;

import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.repositorio.CategoryRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.CommandRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.Repositorio;
import org.olmedo.commands.jdbc.utils.ConexionBaseDeDatos;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Date;


public class Controller {
    public static void main(String[] args) throws SQLException {

        try (Connection conn = ConexionBaseDeDatos.getConnection()) {

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                Repositorio<Category> categoryRepository = new CategoryRepositoryImpl(conn);
                System.out.println("==================== Insert new Category ====================");
                Repositorio<Command> repositorio = new CommandRepositoryImpl(conn);
                Category category = new Category();
                category.setName("Búsqueda y Filtrado de Archivos");
                Category newCategory = categoryRepository.save(category);
                System.out.println("Category save succesfull" + newCategory.getId());

                System.out.println("================== findAll ====================");
                repositorio.findAll().forEach(System.out::println);

                System.out.println("================== byId ====================");
                System.out.println(repositorio.byId(5L));

                System.out.println("================== New Commands ====================");
                Command command = new Command();
                command.setName("find");
                command.setDescription("Busca archivos o directorios que cumplan ciertas condiciones");
                command.setExample("find /home/user -name ");
                command.setRegistrationDate(new Date());


                command.setCategory(newCategory);

                repositorio.save(command);
                System.out.println("Command save succesfull" + command.getId());
                repositorio.findAll().forEach(System.out::println);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
