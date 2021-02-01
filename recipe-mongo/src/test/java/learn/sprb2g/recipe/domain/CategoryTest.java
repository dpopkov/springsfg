package learn.sprb2g.recipe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testGetId() {
        final String idValue = "42";
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }
}