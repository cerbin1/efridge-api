package pl.efridge.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.efridge.api.model.Ingredient;
import pl.efridge.api.model.Recipe;
import pl.efridge.api.service.RecipeService;

import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
public class ApiRestController {
    private final RecipeService recipeService;

    @Autowired
    public ApiRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipes/all")
    @ResponseBody
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), OK);
    }

    @GetMapping("recipes/{id}")
    @ResponseBody
    public ResponseEntity<Recipe> getRecipeById(
            @PathVariable long id) {
        return new ResponseEntity<>(recipeService.getRecipeById(id), OK);
    }

    @GetMapping("recipes/{recipeId}/missingIngredients")
    public ResponseEntity<List<Ingredient>> getListOfMissingIngredientsBasedOnOwned(
            @PathVariable Long recipeId,
            @RequestBody List<Ingredient> ingredients) {
        List<Ingredient> listOfMissingIngredientsBasedOnRecipe =
                recipeService.getListOfMissingIngredients(recipeId, ingredients);
        if (listOfMissingIngredientsBasedOnRecipe == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(listOfMissingIngredientsBasedOnRecipe, OK);
    }

    @CrossOrigin
    @PostMapping("recipes/available")
    public ResponseEntity<List<Recipe>> getListOfAvailableRecipesBasedOnOwnedIngredients(
            @RequestBody String jsonAsString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {
        }.getType();
        List<Ingredient> ingredientsOwned = gson.fromJson(jsonAsString, listType);
        List<Recipe> availableRecipes = recipeService.getListOfAvailableRecipes(ingredientsOwned);
        return new ResponseEntity<>(availableRecipes, OK);
    }

    @GetMapping("recipes/findByName")
    public ResponseEntity<List<Recipe>> getListOfRecipesWithName(
            @RequestParam String name) {
        List<Recipe> recipesWithName = recipeService.getListOfRecipesWithName(name);
        return new ResponseEntity<>(recipesWithName, OK);
    }
}
