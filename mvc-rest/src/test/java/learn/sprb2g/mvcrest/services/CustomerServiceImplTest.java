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
    @Mock
    CustomerRepository customerRepository;

    CustomerServiceImpl service;

    @BeforeEach
    void setup() {
        service = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
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
        final Long customerId = 42L;
        customer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> opt = service.getCustomerById(customerId);
        assertNotNull(opt);
        assertTrue(opt.isPresent());
        assertEquals(customerId, opt.get().getId());
    }
}
