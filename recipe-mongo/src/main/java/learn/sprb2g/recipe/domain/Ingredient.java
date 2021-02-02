package learn.sprb2g.recipe.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Ingredient {

    private String id = UUID.randomUUID().toString();
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
