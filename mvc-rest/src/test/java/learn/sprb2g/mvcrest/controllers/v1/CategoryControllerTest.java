package learn.sprb2g.mvcrest.controllers.v1;

import learn.sprb2g.mvcrest.api.v1.model.CategoryDTO;
import learn.sprb2g.mvcrest.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private static final String NAME_1 = "category_name_1";
    private static final String NAME_2 = "category_name_2";
    private static final Long ID_1 = 42L;
    private static final Long ID_2 = 43L;

    @Mock
    CategoryService categoryService;
    @InjectMocks
    CategoryController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO(NAME_1);
        category1.setId(ID_1);
        CategoryDTO category2 = new CategoryDTO(NAME_2);
        category2.setId(ID_2);
        List<CategoryDTO> categories = List.of(category1, category2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get(CategoryController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    void testGetByName() throws Exception {
        CategoryDTO category = new CategoryDTO(NAME_1);
        category.setId(ID_1);
        when(categoryService.getCategoryByName(NAME_1)).thenReturn(Optional.of(category));

        mockMvc.perform(get(CategoryController.BASE_URL + NAME_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));
    }

    @Test
    void testGetByNameNotFound() throws Exception {
        when(categoryService.getCategoryByName(NAME_1)).thenReturn(Optional.empty());

        mockMvc.perform(get(CategoryController.BASE_URL + NAME_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
        verify(categoryService).getCategoryByName(anyString());
    }
}
