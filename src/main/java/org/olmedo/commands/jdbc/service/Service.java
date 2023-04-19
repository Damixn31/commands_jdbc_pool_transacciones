package org.olmedo.commands.jdbc.service;

import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.models.Category;

import java.sql.SQLException;

import java.util.List;

public interface Service {
  //commands
  List<Command> list() throws SQLException;
  Command byId(Long id) throws SQLException;
  Command save(Command command) throws SQLException;
  void delete(Long id) throws SQLException;

  //Category
  List<Category> listCategory() throws SQLException;
  Category byIdCategory(Long id) throws SQLException;
  Category saveCategory(Category category) throws SQLException;
  void deleteCategory(Long id) throws SQLException;



  // command with category
  void saveCommandCategory(Command command, Category category) throws SQLException;
}
