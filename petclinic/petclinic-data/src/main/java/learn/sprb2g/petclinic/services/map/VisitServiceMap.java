package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.model.Visit;
import learn.sprb2g.petclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceMap extends AbstractMapService<Visit> implements VisitService {

    @Override
    public Visit save(Visit visit) {
        validate(visit);
        return super.save(visit);
    }

    private void validate(Visit visit) {
        if (visit == null) {
            throw new IllegalArgumentException("Visit cannot be null");
        }
        Pet pet = visit.getPet();
        if (pet == null) {
            throw new IllegalArgumentException("Visit cannot have empty Pet");
        }
        if (pet.getId() == null) {
            throw new IllegalArgumentException("Visit cannot have Pet without ID");
        }
        Owner owner = pet.getOwner();
        if (owner == null) {
            throw new IllegalArgumentException("Visit cannot have Pet without Owner");
        }
        if (owner.getId() == null) {
            throw new IllegalArgumentException("Visit cannot have Pet's Owner without ID");
        }
    }
}
