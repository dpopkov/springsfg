package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    private Set<Owner> owners;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = Set.of(
                Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build()
        );
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/owners/index", "/owners/index.html"})
    void testList(String urlTemplate) throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void testInitFindOwnersForm() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void testProcessFormReturnMany() throws Exception {
        List<Owner> ownerList = List.of(
                Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build()
        );
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void testProcessFormReturnOne() throws Exception {
        final Long ownerId = 1L;
        List<Owner> ownerList = List.of(Owner.builder().id(ownerId).build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));
    }

    @Test
    void testProcessFormReturnNothing() throws Exception {
        List<Owner> ownerList = List.of();
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testShowOwner() throws Exception {
        final Long ownerId = 10L;
        Owner owner = Owner.builder().id(ownerId).build();
        when(ownerService.findById(ownerId)).thenReturn(owner);

        mockMvc.perform(get("/owners/" + ownerId))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(ownerId))));
        verify(ownerService).findById(ownerId);
    }

    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void testProcessCreationForm() throws Exception {
        final Long ownerId = 10L;
        Owner owner = Owner.builder().id(ownerId).build();
        when(ownerService.save(ArgumentMatchers.any(Owner.class))).thenReturn(owner);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));
        verify(ownerService).save(ArgumentMatchers.any(Owner.class));
    }

    @Test
    void testInitUpdateOwnerForm() throws Exception {
        final Long ownerId = 10L;
        Owner owner = Owner.builder().id(ownerId).build();
        when(ownerService.findById(ownerId)).thenReturn(owner);

        mockMvc.perform(get("/owners/" + ownerId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        verify(ownerService).findById(ownerId);
    }

    @Test
    void testProcessUpdateOwnerForm() throws Exception {
        final Long ownerId = 10L;
        Owner owner = Owner.builder().id(ownerId).build();
        when(ownerService.save(ArgumentMatchers.any(Owner.class))).thenReturn(owner);

        mockMvc.perform(post("/owners/" + ownerId + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));
        verify(ownerService).save(ArgumentMatchers.any(Owner.class));
    }
}
