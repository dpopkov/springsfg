package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.exceptions.NotFoundException;
import learn.sprb2g.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void showById() throws Exception {
        final String theId = "10";
        Recipe recipe = Recipe.builder().id(theId).build();
        when(recipeService.findById(anyString())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/" + theId + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).findById(theId);
    }

    @Test
    void testShowByIdNotFound() throws Exception {
        final String notExistingId = "10";
        when(recipeService.findById(notExistingId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/" + notExistingId + "/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
        verify(recipeService).findById(notExistingId);
    }

    @Test
    void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("20");
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some string")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/20/show"));
    }

    @Test
    void testPostNewRecipeFormValidationFail() throws Exception {
        mockMvc.perform(post("/recipe/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "")
                .param("cookTime", "3000")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
        verifyNoInteractions(recipeService);
    }

    @Test
    void testGetUpdateView() throws Exception {
        final String theId = "30";
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(theId);
        when(recipeService.findCommandById(theId)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + theId + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        final String deleteId = "10";
        mockMvc.perform(get("/recipe/" + deleteId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService).deleteById(deleteId);
    }
}
