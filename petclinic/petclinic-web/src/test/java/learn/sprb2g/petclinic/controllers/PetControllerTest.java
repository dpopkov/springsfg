package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.model.PetType;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.PetService;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    @Mock
    PetService petService;
    @Mock
    PetTypeService petTypeService;
    @Mock
    OwnerService ownerService;

    @InjectMocks
    PetController petController;

    private MockMvc mockMvc;

    private final Long ownerId = 10L;
    private Owner owner;
    private Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ownerId).build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void testInitCreationForm() throws Exception {
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/" + ownerId + "/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(model().attributeExists("pet"));
        verifyNoInteractions(petService);
    }

    @Test
    void testProcessCreationForm() throws Exception {
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.save(any())).thenReturn(new Pet());

        mockMvc.perform(post("/owners/" + ownerId + "/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));
        verify(petService).save(any(Pet.class));
    }

    @Test
    void testInitUpdateForm() throws Exception {
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        final Long petId = 30L;
        Pet pet = Pet.builder().id(petId).build();
        when(petService.findById(petId)).thenReturn(pet);

        mockMvc.perform(get("/owners/" + ownerId + "/pets/" + petId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(model().attributeExists("pet"));
        verify(petService).findById(petId);
    }

    @Test
    void testProcessUpdateForm() throws Exception {
        final Long petId = 30L;
        Pet pet = Pet.builder().id(petId).build();
        owner.addPet(pet);
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.save(any())).thenReturn(pet);

        mockMvc.perform(post("/owners/" + ownerId + "/pets/" + petId + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));
        verify(petService).save(any(Pet.class));
    }
}
