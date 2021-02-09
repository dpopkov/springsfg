package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.mapper.CustomerMapper;
import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;
import learn.sprb2g.mvcrest.bootstrap.BootstrapData;
import learn.sprb2g.mvcrest.domain.Customer;
import learn.sprb2g.mvcrest.repositories.CategoryRepository;
import learn.sprb2g.mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerServiceImplIT {
    private static final String UPDATED_FIRST_NAME = "James Updated";
    private static final String UPDATED_LAST_NAME = "Gosling Updated";

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private CustomerServiceImpl service;

    @BeforeEach
    void setup() {
        BootstrapData bootstrapData = new BootstrapData(categoryRepository, customerRepository);
        bootstrapData.run();
        service = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void testPatchCustomerUpdateFirstName() {
        final Long customerId = getCustomerIdValue();
        Customer original = customerRepository.getOne(customerId);
        String originalFirstname = original.getFirstname();
        String originalLastname = original.getLastname();

        CustomerDTO dto = new CustomerDTO();
        dto.setFirstname(UPDATED_FIRST_NAME);

        CustomerDTO patched = service.patchCustomer(customerId, dto);

        assertEquals(UPDATED_FIRST_NAME, patched.getFirstname());
        Customer found = customerRepository.getOne(customerId);
        assertEquals(UPDATED_FIRST_NAME, found.getFirstname());
        assertThat(found.getFirstname(), not(equalTo(originalFirstname)));
        assertThat(found.getLastname(), equalTo(originalLastname));
    }

    @Test
    void testPatchCustomer() {
        final Long customerId = getCustomerIdValue();
        Customer original = customerRepository.getOne(customerId);
        String originalFirstname = original.getFirstname();
        String originalLastname = original.getLastname();

        CustomerDTO dto = new CustomerDTO();
        dto.setLastname(UPDATED_LAST_NAME);

        CustomerDTO patched = service.patchCustomer(customerId, dto);

        assertEquals(UPDATED_LAST_NAME, patched.getLastname());
        Customer found = customerRepository.getOne(customerId);
        assertEquals(UPDATED_LAST_NAME, found.getLastname());
        assertThat(found.getFirstname(), equalTo(originalFirstname));
        assertThat(found.getLastname(), not(equalTo(originalLastname)));
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new NoSuchElementException("Cannot get customer because repository is empty.");
        }
        return customers.get(0).getId();
    }
}
