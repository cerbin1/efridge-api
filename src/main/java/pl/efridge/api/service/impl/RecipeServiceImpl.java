package pl.efridge.api.service.impl;

import org.springframework.stereotype.Service;
import pl.efridge.api.model.Ingredient;
import pl.efridge.api.model.Recipe;
import pl.efridge.api.service.RecipeService;

import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Override
    public List<Recipe> getAllRecipes() {
        return Arrays.asList(new Recipe(1, "Gulasz z parówek", "Przygotowanie:\n" +
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
                new Recipe(2, "Ryż z parówkami", "Przygotowanie:\n" +
                        "Ryż ugotować na sypko.\n" +
                        "Odsączyć i wymieszać z odrobiną oliwy z oliwek.\n" +
                        "Cebulę pokroić w kostkę i zeszklić na patelni.\n" +
                        "Parówki obrać i pokroić w talarki, paprykę pokroić w kostkę.\n" +
                        "Wszystko dodać do cebuli i dusić ok. 10-15 minut.\n" +
                        "Dodać ryż, wymieszać i doprawić do smaku.\n" +
                        "Posypać posiekaną natką pietruszki.", Arrays.asList(
                        new Ingredient(6, "Ryż", 2, "sztuka"),
                        new Ingredient(1, "Parówka", 6, "gram"),
                        new Ingredient(7, "Papryka", 2, "sztuka"),
                        new Ingredient(3, "Cebula", 2, "sztuka")
                ))
        );
    }
}