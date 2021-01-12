package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.exceptions.NotFoundException;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();

    /**
     * Finds {@link Recipe} by id.
     * @param id the id
     * @return recipe with the specified id
     * @throws NotFoundException if cannot find by id
     */
    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
