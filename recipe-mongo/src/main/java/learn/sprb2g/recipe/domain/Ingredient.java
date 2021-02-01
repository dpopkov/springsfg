package learn.sprb2g.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class Ingredient {

    @Id
    private String id;
    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure unit;

    public Ingredient() {
    }

    public Ingredient(String description, double amount, UnitOfMeasure unit) {
        this.description = description;
        this.amount = BigDecimal.valueOf(amount);
        this.unit = unit;
    }
}
