package com.mk.RecipeApplication.ingredient;

import javax.persistence.*;

/**
 * Represents an ingredient used in a recipe.
 * This entity maps to the "ingredients" table in the database.
 */
@Entity
@Table(name="ingredients")
public class Ingredient {

    /**
     * Unique identifier for the ingredient.
     * Auto-generated using a sequence strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredients_id_seq")
    @SequenceGenerator(name = "ingredients_id_seq", sequenceName = "ingredients_id_seq", allocationSize = 1)
    private int id;

    /**
     * ID of the recipe to which this ingredient belongs.
     */
    private int recipeId;

    /**
     * Specifies the order of the ingredient in the recipe's ingredient list.
     */
    private int orderForList;

    /**
     * Name of the ingredient (e.g., "Sugar", "Flour").
     */
    private String name;

    /**
     * Unit of measurement for the ingredient (e.g., "grams", "cups").
     */
    private String measuringUnit;

    /**
     * Quantity of the ingredient in the specified measuring unit.
     */
    private float amount;

    /**
     * Full constructor to initialize all fields.
     *
     * @param id unique identifier for the ingredient
     * @param recipeId ID of the associated recipe
     * @param orderForList order of the ingredient in the list
     * @param name name of the ingredient
     * @param measuringUnit unit of measurement
     * @param amount quantity of the ingredient
     */
    public Ingredient(int id, int recipeId, int orderForList, String name, String measuringUnit, float amount) {
        this.id = id;
        this.recipeId = recipeId;
        this.orderForList = orderForList;
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.amount = amount;
    }

    /**
     * Constructor without ID, typically used when creating a new ingredient
     * that has not yet been persisted to the database.
     *
     * @param recipeId ID of the associated recipe
     * @param orderForList order of the ingredient in the list
     * @param name name of the ingredient
     * @param measuringUnit unit of measurement
     * @param amount quantity of the ingredient
     */
    public Ingredient(int recipeId, int orderForList, String name, String measuringUnit, float amount) {
        this.recipeId = recipeId;
        this.orderForList = orderForList;
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.amount = amount;
    }

    /**
     * Default constructor required for JPA.
     */
    public Ingredient() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Resets the ID of the ingredient (for testing or special cases).
     * <b>Note:</b> This method is potentially problematic, as parsing
     * `null` as an integer will cause an exception. Use cautiously.
     */
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
