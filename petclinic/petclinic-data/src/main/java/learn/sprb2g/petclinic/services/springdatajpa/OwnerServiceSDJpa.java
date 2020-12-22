package learn.sprb2g.petclinic.services.springdatajpa;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.repositories.OwnerRepository;
import learn.sprb2g.petclinic.repositories.PetRepository;
import learn.sprb2g.petclinic.repositories.PetTypeRepository;
import learn.sprb2g.petclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("spring-data-jpa")
public class OwnerServiceSDJpa implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;

    public OwnerServiceSDJpa(OwnerRepository ownerRepository, PetRepository petRepository,
                             PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> set = new HashSet<>();
        ownerRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Owner save(Owner entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        return ownerRepository.save(entity);
    }

    @Override
    public void delete(Owner entity) {
        ownerRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }
}
