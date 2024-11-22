package com.mk.RecipeApplication.ingredient;

import com.mk.RecipeApplication.recipe.RecipeRepository;
import com.mk.RecipeApplication.recipe.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Ingredient entities.
 * Provides business logic for handling CRUD operations related to ingredients and their association with recipes.
 */
@Service
public class IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * Constructor for initializing IngredientService with required dependencies.
     *
     * @param ingredientRepository the repository for managing Ingredient entities
     * @param recipeService the service for managing Recipe entities (not directly used here but injected for potential use)
     * @param recipeRepository the repository for accessing Recipe entities
     */
    public IngredientService(IngredientRepository ingredientRepository, RecipeService recipeService, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * Retrieves an ingredient by its unique ID.
     *
     * @param id the ID of the ingredient
     * @return an Optional containing the Ingredient if found, or empty if not found
     */
    public Optional<Ingredient> getIngredientById(int id) {
        return ingredientRepository.findById(id);
    }

    /**
     * Retrieves all ingredients stored in the database.
     *
     * @return an Optional containing a list of all ingredients, or empty if none are found
     */
    public Optional<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return Optional.of(ingredients);
    }

    /**
     * Retrieves all ingredients associated with a specific recipe by its ID.
     *
     * @param recipeId the ID of the recipe
     * @return an Optional containing a list of ingredients for the specified recipe, or empty if not found
     */
    public Optional<List<Ingredient>> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(recipeId);
        return Optional.of(ingredients);
    }

    /**
     * Creates a new ingredient for a specified recipe.
     *
     * @param recipeId the ID of the recipe to which the ingredient belongs
     * @param ingredient the Ingredient object to be created
     * @return the created Ingredient Object
     * @throws Exception if the recipe does not exist
     */
    public Ingredient createIngredient(int recipeId, Ingredient ingredient) throws Exception {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe for ingredient cannot be found"));

        return ingredientRepository.save(ingredient);
    }

    /**
     * Creates multiple new ingredients for a specified recipe.
     *
     * @param recipeId the ID of the recipe to which the ingredients belong
     * @param ingredients a list of Ingredients objects to be created
     * @return the list of created Ingredient objects
     * @throws Exception if the recipe does not exit
     */
    public List<Ingredient> createIngredients(int recipeId, List<Ingredient> ingredients) throws Exception {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe for ingredient cannot be found"));

        return ingredientRepository.saveAll(ingredients);
    }

    /**
     * Updates an existing ingredient by its ID.
     *
     * @param id the ID of the ingredient to be updated
     * @param ingredient the updated Ingredient object
     * @return the updated Ingredient object
     * @throws Exception if the ingredient does not exist or there is an error during the update
     */
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

    /**
     * Updates multiple ingredients associated with a specific recipe ID.
     * Deletes the old ingredients and saves the new ones.
     *
     * @param recipeId the ID of the recipe
     * @param ingredients the list of updated Ingredient objects
     * @return the list of updated Ingredient objects
     * @throws Exception if there is an error during the update
     */
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

    /**
     * Deletes an ingredient by its ID.
     *
     * @param id the ID of the Ingredient to be deleted
     * @throws Exception if the ingredient does not exist
     */
    public void deleteIngredient(int id) throws Exception {
        ingredientRepository.findById(id)
                .orElseThrow(() -> new Exception("Ingredient not found"));

        ingredientRepository.deleteById(id);
    }

    /**
     * Deletes all ingredient associated with a specific recipe ID.
     *
     * @param recipeId the ID of the recipe where the ingredients will be deleted
     * @throws Exception if no ingredients are found for the recipe
     */
    public void deleteIngredientsByRecipeId(int recipeId) throws Exception {
        List<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(recipeId);
        if(ingredients.isEmpty()) {
            throw new Exception("Ingredients not found");
        }

        ingredientRepository.deleteAll(ingredients);
    }
}
