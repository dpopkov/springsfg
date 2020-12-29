package learn.sprb2g.petclinic.services.map;

import learn.sprb2g.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerService;
    private final Long ownerId = 1L;
    private final String lastName = "Dow";

    @BeforeEach
    void setUp() {
        ownerService = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        ownerService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void testFindAll() {
        Set<Owner> all = ownerService.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void testFindById() {
        Owner owner = ownerService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void testSave() {
        final long id = 2L;
        Owner owner = Owner.builder().id(id).build();
        Owner saved = ownerService.save(owner);
        assertEquals(id, saved.getId());
        assertEquals(2, ownerService.findAll().size());
    }

    @Test
    void testSaveNoId() {
        Owner saved = ownerService.save(Owner.builder().build());
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    void testDelete() {
        Owner found = ownerService.findById(ownerId);
        ownerService.delete(found);
        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    void testDeleteById() {
        ownerService.deleteById(ownerId);
        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner found = ownerService.findByLastName(lastName);
        assertNotNull(found);
        assertEquals(lastName, found.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        Owner found = ownerService.findByLastName("Absent");
        assertNull(found);
    }
}
