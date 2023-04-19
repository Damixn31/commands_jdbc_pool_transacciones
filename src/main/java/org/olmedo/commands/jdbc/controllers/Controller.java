package org.olmedo.commands.jdbc.controllers;

import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.repositorio.CategoryRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.CommandRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.Repositorio;
import org.olmedo.commands.jdbc.service.CatalogueService;
import org.olmedo.commands.jdbc.service.Service;
import org.olmedo.commands.jdbc.utils.ConexionBaseDeDatos;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Date;


public class Controller {
    public static void main(String[] args) throws SQLException {

        Service service = new CatalogueService();

        System.out.println("================== findAll ====================");
        service.list().forEach(System.out::println);

        Category category = new Category();
        category.setName("Compresión y Descompresión");


        Command command = new Command();
        command.setName("unzip");
        command.setDescription("Descomprime archivos");
        command.setExample("unzip archivo.zip");
        command.setRegistrationDate(new Date());

        service.saveCommandCategory(command, category);

        System.out.println("Command save succesfull " + command.getId());
        service.list().forEach(System.out::println);


    }
}

