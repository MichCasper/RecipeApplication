package com.mk.RecipeApplication.recipe;


import javax.persistence.*;

@Entity
@Table(name="recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipes_id_seq")
    @SequenceGenerator(name = "recipes_id_seq", sequenceName = "recipes_id_seq", allocationSize = 1)
    private int id;
    private String name;
    private boolean isVegetarian;
    private String description;
    private int cookingTime;

    public Recipe(int id, String name, boolean isVegetarian, String description, int cookingTime) {
        this.id = id;
        this.name = name;
        this.isVegetarian = isVegetarian;
        this.description = description;
        this.cookingTime = cookingTime;
    }

    public Recipe(int cookingTime, String description, boolean isVegetarian, String name) {
        this.cookingTime = cookingTime;
        this.description = description;
        this.isVegetarian = isVegetarian;
        this.name = name;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }
}
