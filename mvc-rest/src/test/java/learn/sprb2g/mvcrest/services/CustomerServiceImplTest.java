package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.mapper.CustomerMapper;
import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;
import learn.sprb2g.mvcrest.domain.Customer;
import learn.sprb2g.mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    private static final Long CUSTOMER_ID = 42L;
    private static final String FIRST_NAME = "Alice";

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    CustomerRepository customerRepository;

    CustomerServiceImpl service;

    @BeforeEach
    void setup() {
        service = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customers = List.of(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> all = service.getAllCustomers();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    void testFindById() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> opt = service.getCustomerById(CUSTOMER_ID);
        assertNotNull(opt);
        assertTrue(opt.isPresent());
        assertEquals(CUSTOMER_ID, opt.get().getId());
    }

    @Test
    void testCreateNewCustomer() {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstname(FIRST_NAME);
        Customer saved = customerMapper.customerDtoToCustomer(customerDto);
        saved.setId(CUSTOMER_ID);
        when(customerRepository.save(any(Customer.class))).thenReturn(saved);

        CustomerDTO savedDto = service.createNewCustomer(customerDto);
        assertNotNull(savedDto);
        assertEquals(CUSTOMER_ID, savedDto.getId());
        assertEquals(FIRST_NAME, savedDto.getFirstname());
        assertEquals(CustomerServiceImpl.API_URL_PREFIX + CUSTOMER_ID, savedDto.getCustomerUrl());
    }
}
