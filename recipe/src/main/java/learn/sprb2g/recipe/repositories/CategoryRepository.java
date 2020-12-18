package learn.sprb2g.recipe.repositories;

import learn.sprb2g.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
