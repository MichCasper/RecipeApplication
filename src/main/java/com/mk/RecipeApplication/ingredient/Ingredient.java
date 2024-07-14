package com.mk.RecipeApplication.ingredient;


import javax.persistence.*;

@Entity
@Table(name="ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredients_id_seq")
    @SequenceGenerator(name = "ingredients_id_seq", sequenceName = "ingredients_id_seq", allocationSize = 1)
    private int id;
    private int recipeId;
    private int orderForList;
    private String name;
    private String measuringUnit;
    private float amount;

    public Ingredient(int id, int recipeId, int orderForList, String name, String measuringUnit, float amount) {
        this.id = id;
        this.recipeId = recipeId;
        this.orderForList = orderForList;
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.amount = amount;
    }

    public Ingredient(int recipeId, int orderForList, String name, String measuringUnit, float amount) {
        this.recipeId = recipeId;
        this.orderForList = orderForList;
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.amount = amount;
    }

    public Ingredient() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void resetId() {
        this.id = Integer.parseInt(null);
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getOrderForList() {
        return orderForList;
    }

    public void setOrderForList(int orderForList) {
        this.orderForList = orderForList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
