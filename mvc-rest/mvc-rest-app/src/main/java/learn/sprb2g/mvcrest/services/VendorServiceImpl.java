package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.mapper.VendorMapper;
import learn.sprb2g.mvcrest.api.v1.model.VendorDTO;
import learn.sprb2g.mvcrest.domain.Vendor;
import learn.sprb2g.mvcrest.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    public static final String API_URL_PREFIX = "/api/v1/vendors/";
    private static final String NOT_FOUND_BY_ID = "Vendor not found by ID ";

    private final VendorRepository vendorRepository;
    private final VendorMapper mapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper mapper) {
        this.vendorRepository = vendorRepository;
        this.mapper = mapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VendorDTO> getVendorById(Long id) {
        return vendorRepository.findById(id).map(this::mapToDto);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = mapper.vendorDtoToVendor(vendorDTO);
        return mapToDto(vendorRepository.save(vendor));
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) throws ResourceNotFoundException {
        if (!vendorRepository.existsById(id)) {
            throw notFoundException(id);
        }
        Vendor vendor = mapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        return mapToDto(vendorRepository.save(vendor));
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) throws ResourceNotFoundException {
        return vendorRepository.findById(id).map(vendor -> {
            // If the Vendor type acquires more properties, add more if/set statements.
            if (vendorDTO.getName() != null ) {
                vendor.setName(vendorDTO.getName());
            }
            return mapToDto(vendorRepository.save(vendor));
        }).orElseThrow(() -> notFoundException(id));
    }

    @Override
    public void deleteVendor(Long id) throws ResourceNotFoundException {
        if (!vendorRepository.existsById(id)) {
            throw notFoundException(id);
        }
        vendorRepository.deleteById(id);
    }

    private static ResourceNotFoundException notFoundException(Long id) {
        return new ResourceNotFoundException(NOT_FOUND_BY_ID + id);
    }

    private VendorDTO mapToDto(Vendor vendor) {
        VendorDTO dto = mapper.vendorToVendorDTO(vendor);
        dto.setVendorUrl(getVendorUrl(vendor.getId()));
        return dto;
    }

    private String getVendorUrl(Long id) {
        return API_URL_PREFIX + id;
    }
}
