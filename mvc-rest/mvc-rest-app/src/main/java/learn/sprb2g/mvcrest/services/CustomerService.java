package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDTO(long id, CustomerDTO customerDTO);

    /**
     * Patches customer by the specified id.
     * @param id id of the customer
     * @param customerDTO DTO containing patching data
     * @return DTO containing updated data
     * @throws ResourceNotFoundException when the customer is not found by the ID
     */
    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException;

    /**
     * Deletes customer by the specified id.
     * @param id id of the customer
     * @throws ResourceNotFoundException when the customer is not found by the ID
     */
    void deleteCustomerById(Long id) throws ResourceNotFoundException;
}
