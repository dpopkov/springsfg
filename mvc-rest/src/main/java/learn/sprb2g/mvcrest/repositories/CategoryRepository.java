package learn.sprb2g.mvcrest.repositories;

import learn.sprb2g.mvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
