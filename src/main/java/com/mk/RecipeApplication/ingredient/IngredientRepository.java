package com.mk.RecipeApplication.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Ingredient entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    /**
     * Retrieves all ingredients associated with the given recipe ID.
     *
     * @param recipeId the ID of the recipe whose ingredients are to be fetched
     * @return a list of ingredients belonging to the specified recipe
     */
    List<Ingredient> findAllByRecipeId(Integer recipeId);
}
