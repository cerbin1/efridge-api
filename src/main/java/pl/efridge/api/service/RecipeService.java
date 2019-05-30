package pl.efridge.api.service;

import pl.efridge.api.model.Ingredient;
import pl.efridge.api.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe findById(Long id);

    List<Ingredient> getListOfMissingIngredients(long recipeId, List<Ingredient> ownedIngredients);

    List<Recipe> getListOfAvailableRecipes(List<Ingredient> ingredientsOwned);

    List<Recipe> getListOfRecipesWithName(String name);

    Recipe getRecipeById(long id);
}
