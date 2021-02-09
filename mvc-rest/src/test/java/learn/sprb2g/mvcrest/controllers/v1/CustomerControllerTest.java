package learn.sprb2g.mvcrest.controllers.v1;

import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;
import learn.sprb2g.mvcrest.services.CustomerService;
import learn.sprb2g.mvcrest.services.CustomerServiceImpl;
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
    private static final String CUSTOMER_URL = CustomerServiceImpl.API_URL_PREFIX + CUSTOMER_ID;

    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<CustomerDTO> customerDTOs = List.of(new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerDTOs);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        final Long customerId = 42L;
        CustomerDTO customer = new CustomerDTO();
        customer.setId(customerId);
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/v1/customers/" + customerId)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(customerId.intValue())));
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

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }
}