package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();
}
