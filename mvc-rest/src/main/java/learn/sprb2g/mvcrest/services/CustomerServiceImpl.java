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
                .map(customer -> {
                    CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);
                    dto.setCustomerUrl(API_URL_PREFIX + customer.getId());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id).map(customer -> {
            CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);
            dto.setCustomerUrl(API_URL_PREFIX + customer.getId());
            return dto;
        });
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

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer saved = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(saved);
        returnDto.setCustomerUrl(API_URL_PREFIX + saved.getId());
        return returnDto;
    }
}
