package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.services.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner> implements OwnerService {
    @Override
    public Owner findByLastName(String lastName) {
        // This is my temporary naive implementation
        for (Owner owner : findAll()) {
            if (owner.getLastName().equals(lastName)) {
                return owner;
            }
        }
        return null;
    }
}
