package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "service-map"})
public class PetServiceMap extends AbstractMapService<Pet> implements PetService {
}
