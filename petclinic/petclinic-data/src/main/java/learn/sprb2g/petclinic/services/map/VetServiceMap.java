package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Vet;
import learn.sprb2g.petclinic.services.SpecialityService;
import learn.sprb2g.petclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends AbstractMapService<Vet> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Vet cannot be null");
        }
        if (entity.getSpecialities() != null) {
            entity.getSpecialities().forEach(s -> {
                if (s.getId() == null) {
                    s.setId(specialityService.save(s).getId());
                }
            });
        }
        return super.save(entity);
    }
}
