package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.converters.IngredientCommandToIngredient;
import learn.sprb2g.recipe.converters.IngredientToIngredientCommand;
import learn.sprb2g.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import learn.sprb2g.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.reactive.RecipeReactiveRepository;
import learn.sprb2g.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeRepository;
    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;
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
        final String recipeId = "1";
        final String ingredientId = "2";
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        when(recipeRepository.findById(recipeId)).thenReturn(Mono.just(recipe));
        when(recipe.getIngredients()).thenReturn(Set.of(ingredient));

        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId).block();
        assertNotNull(ingredientCommand);
        verify(recipeRepository).findById(recipeId);
        verify(recipe).getIngredients();
    }

    @Test
    void testSaveIngredientCommand() {
        // Given
        final String recipeId = "2";
        final String ingredientId = "3";
        IngredientCommand cmd = new IngredientCommand(ingredientId);
        cmd.setRecipeId(recipeId);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(recipeId)).thenReturn(Mono.just(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        // When
        IngredientCommand savedCmd = ingredientService.saveIngredientCommand(cmd).block();

        // Then
        assertEquals(ingredientId, savedCmd.getId());
        verify(recipeRepository).findById(recipeId);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void testDeleteByRecipeIdAndIngredientId() {
        // Given
        final String recipeId = "2";
        final String ingredientId = "3";
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(recipeId)).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(recipe)).thenReturn(Mono.empty());

        // When
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);

        // Then
        verify(recipeRepository).findById(recipeId);
        verify(recipeRepository).save(any(Recipe.class));
    }
}
