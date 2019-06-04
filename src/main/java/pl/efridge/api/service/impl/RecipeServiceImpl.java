package pl.efridge.api.service.impl;

import org.springframework.stereotype.Service;
import pl.efridge.api.model.Ingredient;
import pl.efridge.api.model.Recipe;
import pl.efridge.api.service.RecipeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private List<Recipe> recipes = Arrays.asList(new Recipe(1, "Gulasz z parówek",
                    "Parówki pokrój w plasterki.\n" +
                            "Pieczarki, czosnek oraz cebulę obierz i pokrój w plasterki.\n" +
                            "Na patelni rozgrzej olej i podsmaż na nim pieczarki, czosnek i cebulę.\n" +
                            "Pomidory sparz, obierz ze skórki i posiekaj.\n" +
                            "W garnuszki zagotuj 0,5 litra wody, dodaj kostkę rosołową, parówki i pomidory.\n" +
                            "Całość duś kilka minut.\n" +
                            "Przed podaniem posyp szczypiorkiem.", Arrays.asList(
            new Ingredient(1, "Parówka", 6, "sztuka"),
            new Ingredient(2, "Pieczarka", 100, "gram"),
            new Ingredient(3, "Cebula", 1, "sztuka"),
            new Ingredient(4, "Pomidor", 2, "sztuka"),
            new Ingredient(5, "Czosnek", 2, "ząbek")
            )),
            new Recipe(2, "Ryż z parówkami",
                    "Ryż ugotować na sypko.\n" +
                            "Odsączyć i wymieszać z odrobiną oliwy z oliwek.\n" +
                            "Cebulę pokroić w kostkę i zeszklić na patelni.\n" +
                            "Parówki obrać i pokroić w talarki, paprykę pokroić w kostkę.\n" +
                            "Wszystko dodać do cebuli i dusić ok. 10-15 minut.\n" +
                            "Dodać ryż, wymieszać i doprawić do smaku.\n" +
                            "Posypać posiekaną natką pietruszki.", Arrays.asList(
                    new Ingredient(6, "Ryż", 2, "sztuka"),
                    new Ingredient(1, "Parówka", 6, "sztuka"),
                    new Ingredient(7, "Papryka", 2, "sztuka"),
                    new Ingredient(3, "Cebula", 2, "sztuka")
            )),
            new Recipe(3, "Leczo z parówką, ogórkiem i papryką",
                    "Przygotowujemy wszystkie składniki potrzebne do przygotowania leczo. Paprykę oczyszczamy z nasion i kroimy w paski. Ogórka obieramy i kroimy w plasterki. Pomidory natomiast na kawałki.\n" +
                            "Parówki obieramy i kroimy w plasterki. Podsmażamy lekko razem z posiekaną cebulą. Wrzucamy paprykę oraz ogórka. Dusimy około 20 minut. Następnie wrzucamy pomidory i dusimy jeszcze chwilę. Doprawiamy do smaku pieprzem oraz solą.", Arrays.asList(
                    new Ingredient(1, "Parówka", 3, "sztuka"),
                    new Ingredient(8, "Ogórek", 1, "sztuka"),
                    new Ingredient(7, "Papryka", 2, "sztuka"),
                    new Ingredient(3, "Cebula", 1, "sztuka"),
                    new Ingredient(4, "Pomidor", 2, "sztuka")
            )),
            new Recipe(4, "Makaron z kurczakiem i pomidorami",
                    "Makaron ugotuj wg opisu na opakowaniu, a następnie odsącz.\n" +
                            "Mięso pokrój w kostkę.\n" +
                            "Cebulę i czosnek obierz i pokrój w kostkę, zeszklij na rozgrzanym maśle razem z mięsem, wkrusz kostkę. Całość podsmaż.\n" +
                            "Dodaj pomidory, duś 10 minut, następnie wlej śmietankę i duś kolejne 10 minut.\n" +
                            "Dopraw pieprzem i bazylią. Połącz całość z makaronem.\n" +
                            "Całość udekoruj listkami świeżej bazylii.\n",
                    Arrays.asList(
                            new Ingredient(14, "Makaron", 200, "gram"),
                            new Ingredient(12, "Filet", 6, "sztuka"),
                            new Ingredient(13, "Sos", 1, "sztuka")
                    )));

    @Override
    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipes
                .stream()
                .filter(recipe -> recipe.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public List<Ingredient> getListOfMissingIngredients(long recipeId, List<Ingredient> ownedIngredients) {
        Recipe recipe = this.findById(recipeId);
        if (recipe == null) {
            return null;
        }

        List<Ingredient> missingIngredients = new ArrayList<>();
        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            long recipeIngredientId = recipeIngredient.getId();
            Ingredient ownedIngredient = getSpecificIngredientFromOwnedIngredients(ownedIngredients, recipeIngredientId);
            if (ownedIngredient == null) {
                missingIngredients.add(recipeIngredient);
            } else {
                int ingredientQuantityNeeded = recipeIngredient.getQuantity() - ownedIngredient.getQuantity();
                if (ingredientQuantityNeeded > 0) {
                    recipeIngredient.setQuantity(ingredientQuantityNeeded);
                    missingIngredients.add(recipeIngredient);
                }
            }
        }
        return missingIngredients;
    }

    @Override
    public List<Recipe> getListOfAvailableRecipes(List<Ingredient> ingredientsOwned) {
        List<Recipe> availableRecipes = new ArrayList<>();
        recipes.forEach(recipe -> {
            if (isEnoughIngredientsForRecipe(recipe, ingredientsOwned)) {
                availableRecipes.add(recipe);
            }
        });
        return availableRecipes;
    }

    @Override
    public List<Recipe> getListOfRecipesWithName(String name) {
        return recipes
                .stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Recipe getRecipeById(long id) {
        return recipes
                .stream()
                .filter(recipe -> recipe.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private Ingredient getSpecificIngredientFromOwnedIngredients(List<Ingredient> ingredientsOwned, long id) {
        return ingredientsOwned
                .stream()
                .filter(tmpIngredient -> tmpIngredient.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private boolean isEnoughIngredientsForRecipe(Recipe recipe, List<Ingredient> ingredientsOwned) {
        List<Ingredient> recipeIngredients = recipe.getIngredients();
        for (Ingredient recipeIngredient : recipeIngredients) {
            long recipeIngredientId = recipeIngredient.getId();
            Ingredient nextIngredientOwned = getSpecificIngredientFromOwnedIngredients(ingredientsOwned, recipeIngredientId);
            if (nextIngredientOwned == null) {
                return false;
            }
            if (recipeIngredient.getQuantity() > nextIngredientOwned.getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
