package learn.sprb2g.spring5demo.repositories;

import learn.sprb2g.spring5demo.model.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
