package learn.sprb2g.petclinic.repositories;

import learn.sprb2g.petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
