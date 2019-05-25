package pl.efridge.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.efridge.api.model.Ingredient;
import pl.efridge.api.model.Recipe;
import pl.efridge.api.service.RecipeService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class ApiRestController {
    private final RecipeService recipeService;

    @Autowired
    public ApiRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipes/all")
    @ResponseBody
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
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
}
