package learn.sprb2g.mvcrest.api.v1.mapper;

import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;
import learn.sprb2g.mvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
    private static final Long ID = 12L;
    private static final String FIRST_NAME = "Alice";
    private static final String LAST_NAME = "Carol";

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    void testCustomerDtoToCustomer() {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(ID);
        dto.setFirstname(FIRST_NAME);
        dto.setLastname(LAST_NAME);
        Customer customer = mapper.customerDtoToCustomer(dto);
        assertEquals(ID, customer.getId());
        assertEquals(FIRST_NAME, customer.getFirstname());
        assertEquals(LAST_NAME, customer.getLastname());
    }
}
