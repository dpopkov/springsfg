package learn.sprb2g.petclinic.repositories;

import learn.sprb2g.petclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
}
