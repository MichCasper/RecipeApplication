package com.mk.RecipeApplication.recipe;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return Optional.of(recipes);
    }
    public Optional<Recipe> getRecipeById(int id) {
        return recipeRepository.findById(id);
    }

    public Recipe createRecipe(Recipe recipe) throws Exception {
            return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(int id, Recipe recipe) throws Exception {
        Recipe recipeToUpdate = recipeRepository.findById(id)
                .orElseThrow(() -> new Exception("Recipe not found"));

        recipeToUpdate.setName(recipe.getName());
        recipeToUpdate.setVegetarian(recipe.isVegetarian());
        recipeToUpdate.setDescription(recipe.getDescription());
        recipeToUpdate.setCookingTime(recipe.getCookingTime());
        try {
            return recipeRepository.save(recipeToUpdate);
        } catch (Exception e) {
            throw new Exception("Error updating recipe!");
        }
    }

    public void deleteRecipe(int id) throws Exception {
        recipeRepository.findById(id)
                .orElseThrow(() -> new Exception("Recipe not found"));

        recipeRepository.deleteById(id);
    }
}
