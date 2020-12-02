package learn.sprb2g.spring5demo.repositories;

import learn.sprb2g.spring5demo.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
