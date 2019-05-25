package pl.efridge.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ingredient {
    private long id;
    private String name;
    private double quantity;
    private String type;
}
