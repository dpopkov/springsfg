package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
