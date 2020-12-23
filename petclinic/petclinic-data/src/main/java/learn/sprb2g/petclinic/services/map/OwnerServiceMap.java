package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.PetService;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
        // This is my temporary naive implementation
        for (Owner owner : findAll()) {
            if (owner.getLastName().equals(lastName)) {
                return owner;
            }
        }
        return null;
    }
}
