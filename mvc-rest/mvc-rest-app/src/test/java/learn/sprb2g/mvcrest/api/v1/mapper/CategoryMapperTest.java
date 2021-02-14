package learn.sprb2g.mvcrest.api.v1.mapper;

import learn.sprb2g.mvcrest.api.v1.model.CategoryDTO;
import learn.sprb2g.mvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private static final String NAME = "Doe";
    private static final long ID = 31L;

    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void testCategoryToCategoryDTO() {
        // Given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        // When
        CategoryDTO dto = categoryMapper.categoryToCategoryDTO(category);

        // Then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }
}
