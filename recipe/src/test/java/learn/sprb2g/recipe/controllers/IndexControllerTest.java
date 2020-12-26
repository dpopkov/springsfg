package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.services.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        var data = Set.of(Recipe.builder().id(1L).build());
        when(recipeService.findAll()).thenReturn(data);
        String page = indexController.getIndexPage(model);

        assertEquals("index", page);
        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute("recipes", data);
    }
}
