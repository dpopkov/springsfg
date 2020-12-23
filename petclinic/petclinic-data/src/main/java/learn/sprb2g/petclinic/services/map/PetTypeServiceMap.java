package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.PetType;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "service-map"})
public class PetTypeServiceMap extends AbstractMapService<PetType> implements PetTypeService {
}
