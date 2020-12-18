package learn.sprb2g.recipe.repositories;

import learn.sprb2g.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
