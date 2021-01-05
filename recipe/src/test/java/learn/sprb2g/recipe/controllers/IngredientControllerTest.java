package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.services.IngredientService;
import learn.sprb2g.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @InjectMocks
    IngredientController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListIngredients() throws Exception {
        final Long theId = 1L;
        when(recipeService.findCommandById(theId)).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipe/" + theId + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).findCommandById(theId);
    }

    @Test
    void testShowSpecificIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        final Long recipeId = 1L;
        final Long ingredientId = 2L;
        when(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId)).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService).findByRecipeIdAndIngredientId(recipeId, ingredientId);
    }
}
