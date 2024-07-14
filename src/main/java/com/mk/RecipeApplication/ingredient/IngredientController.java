package com.mk.RecipeApplication.ingredient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ingredientService.getAllIngredients()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Ingredient>> getAllIngredientsByRecipeId(@PathVariable int recipeId) {
        return ingredientService.getIngredientsByRecipeId(recipeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int id) {
        return ingredientService.getIngredientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/for-recipe/{recipeId}/list")
    public ResponseEntity<List<Ingredient>> createIngredients(@PathVariable int recipeId, @RequestBody List<Ingredient> ingredients) {
        try {
            List<Ingredient> savedIngredients = ingredientService.createIngredients(recipeId, ingredients);
            return ResponseEntity.ok(savedIngredients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/for-recipe/{recipeId}")
    public ResponseEntity<Ingredient> createIngredient(@PathVariable int recipeId, @RequestBody Ingredient ingredient) {
        try {
            Ingredient savedIngredient = ingredientService.createIngredient(recipeId, ingredient);
            return ResponseEntity.ok(savedIngredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Ingredient>> updateIngredients(@PathVariable int recipeId, @RequestBody List<Ingredient> ingredients) {
        try {
            List<Ingredient> updatedIngredients = ingredientService.updateIngredientsByRecipeId(recipeId, ingredients);
            return ResponseEntity.ok(updatedIngredients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        try {
            Ingredient updatedIngredient = ingredientService.updateIngredient(id, ingredient);
            return ResponseEntity.ok(updatedIngredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Ingredient>> deleteIngredients(@PathVariable int recipeId) {
        try {
            ingredientService.deleteIngredientsByRecipeId(recipeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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
