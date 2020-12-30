package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    @ValueSource(strings = {"/owners", "/owners/", "/owners/index", "/owners/index.html"})
    void testList(String urlTemplate) throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void testFind() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(view().name("notimplemented"));
        verifyNoInteractions(ownerService);
    }
}
