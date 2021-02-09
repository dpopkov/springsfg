package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
}
