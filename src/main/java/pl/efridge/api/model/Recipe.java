package pl.efridge.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Recipe {
    private long id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
}
