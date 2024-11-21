package com.mk.RecipeApplication.ingredient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IngredientTest {

    @Test
    public void testIngredientGetterAndSetter() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setRecipeId(1);
        ingredient.setOrderForList(1);
        ingredient.setName("Pasta");
        ingredient.setMeasuringUnit("Gramm");
        ingredient.setAmount(500);

        Assertions.assertEquals(1, ingredient.getId());
        Assertions.assertEquals(1, ingredient.getRecipeId());
        Assertions.assertEquals(1, ingredient.getOrderForList());
        Assertions.assertEquals("Pasta", ingredient.getName());
        Assertions.assertEquals("Gramm", ingredient.getMeasuringUnit());
        Assertions.assertEquals(500, ingredient.getAmount());
    }

    @Test
    public void testIngredientConstructors() {
            Ingredient ingredient = new Ingredient(1, 1, 1, "Pasta", "Gramm", 500);

            Assertions.assertEquals(1, ingredient.getId());
            Assertions.assertEquals(1, ingredient.getRecipeId());
            Assertions.assertEquals(1, ingredient.getOrderForList());
            Assertions.assertEquals("Pasta", ingredient.getName());
            Assertions.assertEquals("Gramm", ingredient.getMeasuringUnit());
            Assertions.assertEquals(500, ingredient.getAmount());

            Ingredient ingredient2 = new Ingredient(2, 2, "Pasta", "Gramm", 500);

            Assertions.assertEquals(2, ingredient2.getRecipeId());
            Assertions.assertEquals(2, ingredient2.getOrderForList());
            Assertions.assertEquals("Pasta", ingredient2.getName());
            Assertions.assertEquals("Gramm", ingredient2.getMeasuringUnit());
            Assertions.assertEquals(500, ingredient2.getAmount());
    }

}
