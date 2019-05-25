package pl.efridge.api.service;

import pl.efridge.api.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
}
