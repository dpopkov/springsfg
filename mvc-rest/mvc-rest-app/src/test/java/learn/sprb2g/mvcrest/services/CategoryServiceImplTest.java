package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.mapper.CategoryMapper;
import learn.sprb2g.mvcrest.api.v1.model.CategoryDTO;
import learn.sprb2g.mvcrest.domain.Category;
import learn.sprb2g.mvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    private static final String NAME = "Bob";
    private static final Long ID = 42L;

    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    @BeforeEach
    void setup() {
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void testGetAllCategories() {
        // Given
        List<Category> categories = List.of(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

        // Then
        assertEquals(3, categoryDTOs.size());
    }

    @Test
    void testGetCategoryName() {
        // Given
        Category category = new Category(NAME);
        category.setId(ID);
        when(categoryRepository.findByName(NAME)).thenReturn(Optional.of(category));
        // When
        var dtoOpt = categoryService.getCategoryByName(NAME);

        // Then
        assertTrue(dtoOpt.isPresent());
        CategoryDTO dto = dtoOpt.get();
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }
}
