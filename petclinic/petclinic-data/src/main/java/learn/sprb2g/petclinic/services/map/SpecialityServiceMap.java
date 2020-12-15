package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Speciality;
import learn.sprb2g.petclinic.services.SpecialityService;
import org.springframework.stereotype.Service;

@Service
public class SpecialityServiceMap extends AbstractMapService<Speciality> implements SpecialityService {
}
