package com.mk.RecipeApplication.ingredient;

import com.mk.RecipeApplication.recipe.RecipeRepository;
import com.mk.RecipeApplication.recipe.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeService recipeService, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public Optional<Ingredient> getIngredientById(int id) {
        return ingredientRepository.findById(id);
    }

    public Optional<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return Optional.of(ingredients);
    }

    public Optional<List<Ingredient>> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(recipeId);
        return Optional.of(ingredients);
    }

    public Ingredient createIngredient(int recipeId, Ingredient ingredient) throws Exception {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe for ingredient cannot be found"));

        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> createIngredients(int recipeId, List<Ingredient> ingredients) throws Exception {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe for ingredient cannot be found"));

        return ingredientRepository.saveAll(ingredients);
    }

    public Ingredient updateIngredient(int id, Ingredient ingredient) throws Exception {
        Ingredient ingredientToUpdate = ingredientRepository.findById(id)
                .orElseThrow(() -> new Exception("Ingredient not found"));

        ingredientToUpdate.setRecipeId(ingredient.getRecipeId());
        ingredientToUpdate.setOrderForList(ingredient.getOrderForList());
        ingredientToUpdate.setName(ingredient.getName());
        ingredientToUpdate.setMeasuringUnit(ingredient.getMeasuringUnit());
        ingredientToUpdate.setAmount(ingredient.getAmount());

        try {
            return ingredientRepository.save(ingredientToUpdate);
        } catch (Exception e) {
            throw new Exception("Error updating ingredient");
        }
    }

    public List<Ingredient> updateIngredientsByRecipeId(int recipeId, List<Ingredient> ingredients) throws Exception {
        List<Ingredient> oldIngredients = ingredientRepository.findAllByRecipeId(recipeId);
        Optional<List<Ingredient>> optionalIngredients = Optional.of(oldIngredients);

        if (optionalIngredients.get().isEmpty()) {
            return ingredientRepository.saveAll(ingredients);
        } else {
            List<Ingredient> ingredientsToBeSaved = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                ingredientsToBeSaved.add(
                        new Ingredient(
                                ingredient.getRecipeId(),
                                ingredient.getOrderForList(),
                                ingredient.getName(),
                                ingredient.getMeasuringUnit(),
                                ingredient.getAmount()));
            }
            List<Ingredient> savedIngredients = ingredientRepository.saveAll(ingredientsToBeSaved);
            ingredientRepository.deleteAll(oldIngredients);
            return savedIngredients;
        }
    }

    public void deleteIngredient(int id) throws Exception {
        ingredientRepository.findById(id)
                .orElseThrow(() -> new Exception("Ingredient not found"));

        ingredientRepository.deleteById(id);
    }

    public void deleteIngredientsByRecipeId(int recipeId) throws Exception {
        List<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(recipeId);
        if(ingredients.isEmpty()) {
            throw new Exception("Ingredients not found");
        }

        ingredientRepository.deleteAll(ingredients);
    }
}
