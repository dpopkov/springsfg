package learn.sprb2g.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
    private final Owner owner = new Owner();

    @Test
    void findsNotNewPet() {
        owner.addPet(Pet.builder().id(10L).name("Spot").build());
        assertNotNull(owner.findPetByName("spot", false));
        assertNotNull(owner.findPetByName("spot", true));
    }

    @Test
    void whenNotIgnoreNewThenFindsNewPet() {
        owner.addPet(Pet.builder().name("Spot").build());
        Pet found = owner.findPetByName("spot", false);
        assertNotNull(found);
    }

    @Test
    void whenIgnoreNewThenDoesNotFindNewPet() {
        owner.addPet(Pet.builder().name("Spot").build());
        Pet found = owner.findPetByName("spot", true);
        assertNull(found);
    }
}
