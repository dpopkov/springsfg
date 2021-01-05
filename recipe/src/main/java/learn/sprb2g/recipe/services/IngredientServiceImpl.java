package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.converters.IngredientToIngredientCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand converter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> opt = recipeRepository.findById(recipeId);
        if (opt.isEmpty()) {
            throw new RuntimeException("Recipe not found ID: " + recipeId);
        }
        Optional<Ingredient> first = opt.get().getIngredients().stream()
                .filter(ing -> ing.getId().equals(ingredientId))
                .findFirst();
        if (first.isEmpty()) {
            throw new RuntimeException("Ingredient not found ID: " + ingredientId);
        }
        return converter.convert(first.get());
    }
}
