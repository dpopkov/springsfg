package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.model.Visit;
import learn.sprb2g.petclinic.services.PetService;
import learn.sprb2g.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    VisitService visitService;
    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void testInitNewVisitForm() throws Exception {
        final Long petId = 20L;
        when(petService.findById(petId)).thenReturn(Pet.builder().id(petId).build());

        mockMvc.perform(get("/owners/10/pets/" + petId + "/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"));
        verify(petService).findById(petId);
    }

    @Test
    void testProcessNewVisitForm() throws Exception {
        final Long ownerId = 10L;
        final Long petId = 20L;
        Owner owner = Owner.builder().id(ownerId).build();
        Pet pet = Pet.builder().id(petId).owner(owner).build();
        when(petService.findById(petId)).thenReturn(pet);

        mockMvc.perform(post("/owners/" + ownerId + "/pets/" + petId + "/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2021-01-21")
                .param("description", "description-value")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));
        verify(visitService).save(ArgumentMatchers.any(Visit.class));
    }
}
