package pl.efridge.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.efridge.api.model.Recipe;
import pl.efridge.api.service.RecipeService;

import java.util.List;

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
}
