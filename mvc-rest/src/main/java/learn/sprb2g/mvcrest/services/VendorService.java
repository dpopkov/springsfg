package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.model.VendorDTO;

import java.util.List;
import java.util.Optional;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    Optional<VendorDTO> getVendorById(Long id);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO) throws ResourceNotFoundException;

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO) throws ResourceNotFoundException;

    void deleteVendor(Long id) throws ResourceNotFoundException;
}
