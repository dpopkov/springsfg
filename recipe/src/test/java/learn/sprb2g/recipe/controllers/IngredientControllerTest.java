package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.services.IngredientService;
import learn.sprb2g.recipe.services.RecipeService;
import learn.sprb2g.recipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
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

    @Test
    void testNewIngredientForm() throws Exception {
        final Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        when(recipeService.findCommandById(recipeId)).thenReturn(recipeCommand);
        when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomAll"));
        verify(recipeService).findCommandById(recipeId);
    }

    @Test
    void testUpdateIngredientForm() throws Exception {
        final Long recipeId = 1L;
        final Long ingredientId = 2L;
        when(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId))
                .thenReturn(new IngredientCommand());
        when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomAll"));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        // Given
        final Long recipeId = 1L;
        final Long ingredientId = 2L;
        IngredientCommand command = new IngredientCommand(ingredientId);
        command.setRecipeId(recipeId);
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        // When/Then
        mockMvc.perform(post("/recipe/" + recipeId + "/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(
                        "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        final Long recipeId = 1L;
        final Long ingredientId = 2L;
        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredients"));
        verify(ingredientService).deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
    }
}
