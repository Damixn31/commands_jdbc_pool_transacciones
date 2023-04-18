package org.olmedo.commands.jdbc.models;

import java.util.Date;

public class Command {
    private Long id;
    private String name;
    private String description;
    private String example;
    private Category category;
    private Date registrationDate;


    public Command() {
    }

    public Command(Long id, String name, String description, String example, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.example = example;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return id +
                " | " +
                name +
                " | " +
                description +
                " | " +
                example +
                " | " +
                category.getName() +
                " | " +
                registrationDate;
    }
}
