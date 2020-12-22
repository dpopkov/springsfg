package learn.sprb2g.petclinic.repositories;

import learn.sprb2g.petclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
