package org.olmedo.commands.jdbc.repositorio;

import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandRepositoryImpl implements Repositorio<Command> {

    private Connection conn;

    public CommandRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Command> findAll() throws SQLException {
        List<Command> commands = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT c.*, ca.name as category FROM commands as c " +
                     "inner join categories as ca ON (c.category_id = ca.id)")) {

            while (rs.next()) {
                Command c = createCommand(rs);

                commands.add(c);
            }

        }
        return commands;
    }

    @Override
    public Command byId(Long id) throws SQLException {
        Command comand = null;

        try (PreparedStatement stmt = conn
                     .prepareStatement("SELECT c.*, ca.name as category FROM commands as c " +
                             "inner join categories as ca ON (c.category_id = ca.id) WHERE c.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    comand = createCommand(rs);
                }

            }
        }
        return comand;
    }

    @Override
    public Command save(Command command) throws SQLException {
        String sql;
        if (command.getId() != null && command.getId() > 0) {
            sql = "UPDATE commands SET name=?, description=?, example=?, category_id=? WHERE id=?";
        } else {
            sql = "INSERT INTO commands(name, description, example, category_id, registration_date) VALUES(?,?,?,?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, command.getName());
            stmt.setString(2, command.getDescription());
            stmt.setString(3, command.getExample());
            stmt.setLong(4, command.getCategory().getId());


            if (command.getId() != null && command.getId() > 0) {
                stmt.setLong(5, command.getId());
            } else {
                stmt.setDate(5, new Date(command.getRegistrationDate().getTime()));
            }
            stmt.executeUpdate();

            if (command.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()) {
                        command.setId(rs.getLong(1));
                    }
                }
            }

            return command;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM commands WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }


    private static Command createCommand(ResultSet rs) throws SQLException {
        Command c = new Command();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setDescription(rs.getString("description"));
        c.setExample(rs.getString("example"));

        Category category = new Category();
        category.setId(rs.getLong("category_id"));
        category.setName(rs.getString("category"));
        c.setCategory(category);

        c.setRegistrationDate(rs.getDate("registration_date"));
        return c;
    }
}
