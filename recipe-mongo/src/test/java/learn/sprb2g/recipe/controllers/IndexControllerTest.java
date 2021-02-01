package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.services.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetIndexPage() {
        var data = Set.of(
                Recipe.builder().id("1").build(),
                Recipe.builder().id("2").build()
        );
        when(recipeService.findAll()).thenReturn(data);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        String page = indexController.getIndexPage(model);

        assertEquals("index", page);
        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> recipes = argumentCaptor.getValue();
        assertEquals(2, recipes.size());
    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
}
