package learn.sprb2g.recipe.converters;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.commands.UnitOfMeasureCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    private static final String ID = "1";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final String DESCRIPTION = "description-text";
    private static final String UNIT_ID = "2";
    public static final String UNIT_DESCRIPTION = "unit-description";

    private final IngredientCommandToIngredient converter = new IngredientCommandToIngredient(
            new UnitOfMeasureCommandToUnitOfMeasure());

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void testConvert() {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setAmount(AMOUNT);
        cmd.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitCmd = new UnitOfMeasureCommand();
        unitCmd.setId(UNIT_ID);
        unitCmd.setDescription(UNIT_DESCRIPTION);
        cmd.setUnit(unitCmd);

        // When
        Ingredient ingredient = converter.convert(cmd);

        // Then
        assertNotNull(ingredient);
        assertEquals(ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        UnitOfMeasure uom = ingredient.getUnit();
        assertNotNull(uom);
        assertEquals(UNIT_ID, uom.getId());
        assertEquals(UNIT_DESCRIPTION, uom.getDescription());
    }

    @Test
    void testConvertNullUnit() {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setAmount(AMOUNT);
        cmd.setDescription(DESCRIPTION);

        // When
        Ingredient ingredient = converter.convert(cmd);

        // Then
        assertNotNull(ingredient);
        assertEquals(ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertNull(ingredient.getUnit());
    }
}
