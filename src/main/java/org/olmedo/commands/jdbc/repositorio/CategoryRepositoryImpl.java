package org.olmedo.commands.jdbc.repositorio;

import org.olmedo.commands.jdbc.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements Repositorio<Category> {
    private Connection conn;

    public CategoryRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categories")) {
            while (rs.next()) {
                categories.add(createCategory(rs));
            }
        }
        return categories;
    }



    @Override
    public Category byId(Long id) throws SQLException {
        Category category = null;
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categories as c WHERE c.id=?")) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = createCategory(rs);
                }
            }
        }
        return category;
    }

    @Override
    public Category save(Category category) throws SQLException {
        String sql = null;
        if(category.getId() != null && category.getId() > 0) {
            sql = "UPDATE categories SET name=? WHERE id=?";
        } else {
            sql = "INSERT INTO categories(name) VALUES(?)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            if (category.getId() != null && category.getId() > 0) {
                stmt.setLong(2, category.getId());
            }
            stmt.executeUpdate();

            if (category.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setId(rs.getLong(1));
                    }
                }
            }
        }
        return category;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM categories WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }

    }


    // metodo
    private static Category createCategory(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        return c;
    }
}
