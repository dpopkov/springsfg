package learn.sprb2g.petclinic.services.springdatajpa;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)     // This annotation sets up the JUnit 5 environment for Mockito
class OwnerSDJpaServiceTest {
    private static final String LAST_NAME = "Doe";
    private static final long THE_ID = 1L;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerSDJpaService ownerService;

    private Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(THE_ID).lastName(LAST_NAME).build();
    }

    @Test
    void testFindByLastName() {
        when(ownerRepository.findByLastName(LAST_NAME)).thenReturn(returnOwner);
        Owner found = ownerService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, found.getLastName());
        verify(ownerRepository, times(1)).findByLastName(LAST_NAME);
    }

    @Test
    void testFindAll() {
        Owner owner2 = Owner.builder().id(2L).build();
        Set<Owner> returnedSet = Set.of(returnOwner, owner2);
        when(ownerRepository.findAll()).thenReturn(returnedSet);
        Set<Owner> all = ownerService.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(returnOwner));
        assertTrue(all.contains(owner2));
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(ownerRepository.findById(THE_ID)).thenReturn(Optional.of(returnOwner));
        Owner found = ownerService.findById(THE_ID);
        assertEquals(THE_ID, found.getId());
        verify(ownerRepository, times(1)).findById(THE_ID);
    }

    @Test
    void testFindByIdNotFound() {
        when(ownerRepository.findById(THE_ID)).thenReturn(Optional.empty());
        Owner found = ownerService.findById(THE_ID);
        assertNull(found);
        verify(ownerRepository, times(1)).findById(THE_ID);
    }

    @Test
    void testSave() {
        Owner owner = new Owner();
        when(ownerRepository.save(owner)).thenReturn(owner);
        Owner saved = ownerService.save(owner);
        assertNotNull(saved);
        verify(ownerRepository).save(owner);
    }

    @Test
    void testDelete() {
        ownerService.delete(returnOwner);
        verify(ownerRepository).delete(returnOwner);
    }

    @Test
    void testDeleteById() {
        ownerService.deleteById(THE_ID);
        verify(ownerRepository).deleteById(THE_ID);
    }
}
