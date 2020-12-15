package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.PetType;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetTypeServiceMap extends AbstractMapService<PetType> implements PetTypeService {
}
