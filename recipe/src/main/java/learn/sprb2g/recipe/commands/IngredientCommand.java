package learn.sprb2g.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private static long lastId = 0L;
    private Long uniqueId;
    {
        synchronized (IngredientCommand.class) {
            uniqueId = ++lastId;
        }
    }
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unit;

    public IngredientCommand(Long id) {
        this.id = id;
    }
}
