package learn.sprb2g.mvcrest.controllers.v1;

import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;
import learn.sprb2g.mvcrest.services.CustomerService;
import learn.sprb2g.mvcrest.services.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest {
    private static final Long CUSTOMER_ID = 42L;
    private static final String FIRST_NAME = "Alice";
    private static final String LAST_NAME = "Abernathy";
    private static final String CUSTOMER_URL = CustomerController.BASE_URL + "/" + CUSTOMER_ID;

    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<CustomerDTO> customerDTOs = List.of(new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerDTOs);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(CUSTOMER_ID);
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        mockMvc.perform(get(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(CUSTOMER_ID.intValue())));
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customer.getFirstname());
        returnDto.setLastname(customer.getLastname());
        returnDto.setCustomerUrl(CUSTOMER_URL);

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customer.getFirstname());
        returnDto.setLastname(customer.getLastname());
        returnDto.setCustomerUrl(CUSTOMER_URL);

        when(customerService.saveCustomerByDTO(eq(CUSTOMER_ID), any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(put(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
        verify(customerService).saveCustomerByDTO(eq(CUSTOMER_ID), any(CustomerDTO.class));
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        String firstnameChanged = FIRST_NAME + " changed";
        customer.setFirstname(firstnameChanged);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(firstnameChanged);
        returnDto.setLastname(LAST_NAME);
        returnDto.setCustomerUrl(CUSTOMER_URL);

        when(customerService.patchCustomer(eq(CUSTOMER_ID), any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(firstnameChanged)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
        verify(customerService).patchCustomer(eq(CUSTOMER_ID), any(CustomerDTO.class));
    }

    @Test
    void testPatchCustomerNotFound() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        String firstnameChanged = FIRST_NAME + " changed";
        customer.setFirstname(firstnameChanged);

        when(customerService.patchCustomer(eq(CUSTOMER_ID), any(CustomerDTO.class)))
                .thenThrow(new ResourceNotFoundException("Customer not found by ID " + CUSTOMER_ID));

        mockMvc.perform(patch(CUSTOMER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer))
        )
                .andExpect(status().isNotFound());
        verify(customerService).patchCustomer(eq(CUSTOMER_ID), any(CustomerDTO.class));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(CUSTOMER_URL))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(CUSTOMER_ID);
    }

    @Test
    void testDeleteCustomerNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(customerService).deleteCustomerById(CUSTOMER_ID);
        mockMvc.perform(delete(CUSTOMER_URL))
                .andExpect(status().isNotFound());
        verify(customerService).deleteCustomerById(CUSTOMER_ID);
    }
}
