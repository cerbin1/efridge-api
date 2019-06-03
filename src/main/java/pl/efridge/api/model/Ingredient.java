package pl.efridge.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private int quantity;
    private String type;
}
