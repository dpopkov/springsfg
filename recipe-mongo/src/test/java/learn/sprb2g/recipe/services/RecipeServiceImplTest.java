package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.converters.RecipeCommandToRecipe;
import learn.sprb2g.recipe.converters.RecipeToRecipeCommand;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.exceptions.NotFoundException;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testFindAll() {
        var data = List.of(
                Recipe.builder().id("1").build(),
                Recipe.builder().id("2").build());
        when(recipeRepository.findAll()).thenReturn(data);
        assertEquals(2, recipeService.findAll().size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        final String theId = "10";
        Recipe recipe = Recipe.builder().id(theId).build();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        Recipe found = recipeService.findById(theId);
        assertNotNull(found);
        assertEquals(theId, found.getId());
        verify(recipeRepository).findById(theId);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void testFindByIdNotExisting() {
        final String theId = "10";
        when(recipeRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.findById(theId));
    }

    @Test
    void testFindCommandById() {
        final String theId = "10";
        Recipe recipe = new Recipe();
        recipe.setId(theId);
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(theId);
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);

        RecipeCommand found = recipeService.findCommandById(theId);
        assertNotNull(found);
        assertEquals(theId, found.getId());
        verify(recipeRepository).findById(theId);
        verify(recipeToRecipeCommand).convert(recipe);
    }

    @Test
    void testDeleteById() {
        final String deleteId = "10";
        recipeService.deleteById(deleteId);
        verify(recipeRepository).deleteById(deleteId);
    }
}
