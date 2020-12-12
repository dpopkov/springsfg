package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Vet;
import learn.sprb2g.petclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends AbstractMapService<Vet> implements VetService {
}
