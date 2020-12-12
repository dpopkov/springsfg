package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.services.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends AbstractMapService<Pet> implements PetService {
}
