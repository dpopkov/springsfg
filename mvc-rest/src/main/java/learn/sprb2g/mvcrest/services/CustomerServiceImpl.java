package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.mapper.CustomerMapper;
import learn.sprb2g.mvcrest.api.v1.model.CustomerDTO;
import learn.sprb2g.mvcrest.domain.Customer;
import learn.sprb2g.mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static final String API_URL_PREFIX = "/api/v1/customer/";

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToDtoAndSetUrl)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::mapToDtoAndSetUrl);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO saveCustomerByDTO(long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException {
        return customerRepository.findById(id).map(customer -> {
            patchCustomerFromDTO(customer, customerDTO);
            return saveAndReturnDTO(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer not found by ID " + id));
    }

    @Override
    public void deleteCustomerById(Long id) throws ResourceNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found by ID " + id);
        }
        customerRepository.deleteById(id);
    }

    private void patchCustomerFromDTO(Customer customer, CustomerDTO customerDTO) {
        if (customerDTO.getFirstname() != null) {
            customer.setFirstname(customerDTO.getFirstname());
        }
        if (customerDTO.getLastname() != null) {
            customer.setLastname(customerDTO.getLastname());
        }
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer saved = customerRepository.save(customer);
        return mapToDtoAndSetUrl(saved);
    }

    private CustomerDTO mapToDtoAndSetUrl(Customer saved) {
        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(saved);
        returnDto.setCustomerUrl(getCustomerUrl(saved.getId()));
        return returnDto;
    }

    private String getCustomerUrl(Long id) {
        return API_URL_PREFIX + id;
    }
}
