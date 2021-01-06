package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.converters.IngredientCommandToIngredient;
import learn.sprb2g.recipe.converters.IngredientToIngredientCommand;
import learn.sprb2g.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import learn.sprb2g.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    Recipe recipe;
    private final IngredientToIngredientCommand converter = new IngredientToIngredientCommand(
            new UnitOfMeasureToUnitOfMeasureCommand());
    private final IngredientCommandToIngredient toIngredientConverter = new IngredientCommandToIngredient(
            new UnitOfMeasureCommandToUnitOfMeasure());

    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setup() {
        ingredientService = new IngredientServiceImpl(
                recipeRepository, unitOfMeasureRepository, converter, toIngredientConverter);
    }

    @Test
    void testFindByRecipeIdAndIngredientId() {
        final Long recipeId = 1L;
        final Long ingredientId = 2L;
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(recipe.getIngredients()).thenReturn(Set.of(ingredient));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);
        assertNotNull(ingredientCommand);
        verify(recipeRepository).findById(recipeId);
        verify(recipe).getIngredients();
    }

    @Test
    void testSaveIngredientCommand() {
        // Given
        final Long recipeId = 2L;
        final Long ingredientId = 3L;
        IngredientCommand cmd = new IngredientCommand(ingredientId);
        cmd.setRecipeId(recipeId);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        // When
        IngredientCommand savedCmd = ingredientService.saveIngredientCommand(cmd);

        // Then
        assertEquals(ingredientId, savedCmd.getId());
        verify(recipeRepository).findById(recipeId);
        verify(recipeRepository).save(any(Recipe.class));
    }
}
