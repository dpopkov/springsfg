package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.converters.IngredientToIngredientCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    IngredientToIngredientCommand converter;
    @Mock
    Recipe recipe;
    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Test
    void testFindByRecipeIdAndIngredientId() {
        final Long recipeId = 1L;
        final Long ingredientId = 2L;
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(recipe.getIngredients()).thenReturn(Set.of(ingredient));
        when(converter.convert(ingredient)).thenReturn(new IngredientCommand());

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);
        assertNotNull(ingredientCommand);
        verify(recipeRepository).findById(recipeId);
        verify(recipe).getIngredients();
        verify(converter).convert(ingredient);
    }
}
