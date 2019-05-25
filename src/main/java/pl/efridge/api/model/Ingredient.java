package pl.efridge.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ingredient {
    private long id;
    private String name;
    private int quantity;
    private String type;
}
