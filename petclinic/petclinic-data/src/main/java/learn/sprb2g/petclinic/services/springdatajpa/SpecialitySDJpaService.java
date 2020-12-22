package learn.sprb2g.petclinic.services.springdatajpa;

import learn.sprb2g.petclinic.model.Speciality;
import learn.sprb2g.petclinic.repositories.SpecialityRepository;
import learn.sprb2g.petclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("spring-data-jpa")
public class SpecialitySDJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialitySDJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> set = new HashSet<>();
        specialityRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Speciality findById(Long id) {
        return specialityRepository.findById(id).orElse(null);
    }

    @Override
    public Speciality save(Speciality entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Speciality cannot be null");
        }
        return specialityRepository.save(entity);
    }

    @Override
    public void delete(Speciality entity) {
        specialityRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        specialityRepository.deleteById(id);
    }
}
