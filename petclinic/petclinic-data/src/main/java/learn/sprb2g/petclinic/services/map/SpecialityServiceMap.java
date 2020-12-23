package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Speciality;
import learn.sprb2g.petclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "service-map"})
public class SpecialityServiceMap extends AbstractMapService<Speciality> implements SpecialityService {
}
