package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.PetService;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Profile({"default", "service-map"})
public class OwnerServiceMap extends AbstractMapService<Owner> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner save(Owner entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (entity.getPets() != null) {
            entity.getPets().forEach(pet -> {
                if (pet.getPetType() == null) {
                    throw new IllegalArgumentException("Pet Type is required");
                }
                if (pet.getPetType().getId() == null) {
                    pet.setPetType(petTypeService.save(pet.getPetType()));
                }
                if (pet.getId() == null) {
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                }
            });
        }
        return super.save(entity);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        Predicate<Owner> predicate;
        if (lastName.startsWith("%")) {
            if (lastName.endsWith("%")) {
                predicate = (o) -> o.getLastName().contains(lastName.substring(1, lastName.length() - 1));
            } else {
                predicate = (o) -> o.getLastName().endsWith(lastName.substring(1));
            }
        } else if (lastName.endsWith("%")) {
            predicate = (o) -> o.getLastName().startsWith(lastName.substring(0, lastName.length() - 1));
        } else {
            predicate = (o) -> o.getLastName().equals(lastName);
        }
        return findAll().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
