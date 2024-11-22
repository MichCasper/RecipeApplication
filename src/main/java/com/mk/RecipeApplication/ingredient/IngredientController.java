package com.mk.RecipeApplication.ingredient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing ingredients.
 * Provides endpoints for CRUD operations on ingredients, both individually and by recipe.
 */
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * Constructor for initializing the controller with the required service.
     *
     * @param ingredientService the service handling ingredient-related logic
     */
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Retrieves all ingredients in the database.
     *
     * @return a list of all ingredients, or a 404 response if no ingredients are found
     */
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ingredientService.getAllIngredients()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all ingredients associated with a specific recipe.
     *
     * @param recipeId the ID of the recipe
     * @return a list of ingredients for the given recipe, or a 404 response if none are found
     */
    @GetMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Ingredient>> getAllIngredientsByRecipeId(@PathVariable int recipeId) {
        return ingredientService.getIngredientsByRecipeId(recipeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a single ingredient by its unique ID.
     *
     * @param id the ID of the ingredient
     * @return the requested ingredient, or a 404 response if it does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int id) {
        return ingredientService.getIngredientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates multiple ingredients for a specific recipe.
     *
     * @param recipeId the ID of the recipe
     * @param ingredients a list of ingredients to create
     * @return the list of saved ingredients, or a 400 response if creation fails
     */
    @PostMapping("/for-recipe/{recipeId}/list")
    public ResponseEntity<List<Ingredient>> createIngredients(@PathVariable int recipeId, @RequestBody List<Ingredient> ingredients) {
        try {
            List<Ingredient> savedIngredients = ingredientService.createIngredients(recipeId, ingredients);
            return ResponseEntity.ok(savedIngredients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Creates a single ingredient for a specific recipe.
     *
     * @param recipeId the ID of the recipe
     * @param ingredient the ingredient to create
     * @return the saved ingredient, or a 400 response if creation fails
     */
    @PostMapping("/for-recipe/{recipeId}")
    public ResponseEntity<Ingredient> createIngredient(@PathVariable int recipeId, @RequestBody Ingredient ingredient) {
        try {
            Ingredient savedIngredient = ingredientService.createIngredient(recipeId, ingredient);
            return ResponseEntity.ok(savedIngredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates multiple ingredients for a specific recipe.
     *
     * @param recipeId the ID of the recipe
     * @param ingredients a list of updated ingredient data
     * @return the list of updated ingredients, or a 400 response if the update fails
     */
    @PutMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Ingredient>> updateIngredients(@PathVariable int recipeId, @RequestBody List<Ingredient> ingredients) {
        try {
            List<Ingredient> updatedIngredients = ingredientService.updateIngredientsByRecipeId(recipeId, ingredients);
            return ResponseEntity.ok(updatedIngredients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates a single ingredient by its ID.
     *
     * @param id the ID of the ingredient to update
     * @param ingredient the updated ingredient data
     * @return the updated ingredient, or a 400 response if the update fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        try {
            Ingredient updatedIngredient = ingredientService.updateIngredient(id, ingredient);
            return ResponseEntity.ok(updatedIngredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes all ingredients associated with a specific recipe.
     *
     * @param recipeId the ID of the recipe
     * @return a 204 response on successful deletion, or a 400 response if the deletion fails
     */
    @DeleteMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Ingredient>> deleteIngredients(@PathVariable int recipeId) {
        try {
            ingredientService.deleteIngredientsByRecipeId(recipeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a single ingredient by its ID.
     *
     * @param id the ID of the ingredient to delete
     * @return a 204 response on successful deletion, or a 400 response if the deletion fails
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable int id) {
        try {
            ingredientService.deleteIngredient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
