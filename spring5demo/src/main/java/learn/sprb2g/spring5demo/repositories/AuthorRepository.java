package learn.sprb2g.spring5demo.repositories;

import learn.sprb2g.spring5demo.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
