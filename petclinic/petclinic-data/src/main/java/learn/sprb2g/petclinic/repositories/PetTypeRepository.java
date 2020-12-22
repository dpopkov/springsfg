package learn.sprb2g.petclinic.repositories;

import learn.sprb2g.petclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
