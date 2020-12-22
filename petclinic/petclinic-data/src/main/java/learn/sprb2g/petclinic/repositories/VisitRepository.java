package learn.sprb2g.petclinic.repositories;

import learn.sprb2g.petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
