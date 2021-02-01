package learn.sprb2g.recipe.converters;

import learn.sprb2g.recipe.commands.UnitOfMeasureCommand;
import learn.sprb2g.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "description-value";

    private final UnitOfMeasureCommandToUnitOfMeasure converter = new UnitOfMeasureCommandToUnitOfMeasure();

    @SuppressWarnings("ConstantConditions")
    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void testConvert() {
        // Given
        UnitOfMeasureCommand cmd = new UnitOfMeasureCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);

        // When
        UnitOfMeasure uom = converter.convert(cmd);

        // Then
        assertNotNull(uom);
        assertEquals(ID, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}
