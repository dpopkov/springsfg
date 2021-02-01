package learn.sprb2g.recipe.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure unit;
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String description, double amount, UnitOfMeasure unit) {
        this.description = description;
        this.amount = BigDecimal.valueOf(amount);
        this.unit = unit;
    }

    public Ingredient(String description, double amount, UnitOfMeasure unit, Recipe recipe) {
        this.description = description;
        this.amount = BigDecimal.valueOf(amount);
        this.unit = unit;
        this.recipe = recipe;
    }

}
