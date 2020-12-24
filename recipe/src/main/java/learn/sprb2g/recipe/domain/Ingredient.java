package learn.sprb2g.recipe.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unit;

    @ManyToOne
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
