package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.mapper.VendorMapper;
import learn.sprb2g.mvcrest.api.v1.model.VendorDTO;
import learn.sprb2g.mvcrest.domain.Vendor;
import learn.sprb2g.mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static learn.sprb2g.mvcrest.services.VendorServiceImpl.API_URL_PREFIX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    private static final Long VENDOR_ID = 42L;
    private static final String NAME = "vendor_name";
    private static final String VENDOR_URL = API_URL_PREFIX + VENDOR_ID;

    @Mock
    VendorRepository vendorRepository;
    final VendorMapper mapper = VendorMapper.INSTANCE;

    VendorServiceImpl service;

    @BeforeEach
    void setup() {
        service = new VendorServiceImpl(vendorRepository, mapper);
    }

    @Test
    void testGetAllVendors() {
        when(vendorRepository.findAll()).thenReturn(List.of(new Vendor("V1"), new Vendor("V2")));

        List<VendorDTO> all = service.getAllVendors();
        assertEquals(2, all.size());
    }

    @Test
    void testGetVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(vendor));

        Optional<VendorDTO> foundOpt = service.getVendorById(VENDOR_ID);
        assertTrue(foundOpt.isPresent());
        assertEquals(VENDOR_ID, foundOpt.get().getId());
    }

    @Test
    void testGetVendorByIdNotFound() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.empty());

        Optional<VendorDTO> foundOpt = service.getVendorById(VENDOR_ID);
        assertTrue(foundOpt.isEmpty());
    }

    @Test
    void testCreateNewVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName(NAME);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO inputDTO = new VendorDTO();
        inputDTO.setName(NAME);

        VendorDTO created = service.createNewVendor(inputDTO);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(NAME, created.getName());
        assertEquals(VENDOR_URL, created.getVendorUrl());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void testUpdateVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName(NAME);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorRepository.existsById(VENDOR_ID)).thenReturn(true);

        VendorDTO inputDTO = new VendorDTO();
        inputDTO.setName(NAME);

        VendorDTO updated = service.updateVendor(VENDOR_ID, inputDTO);
        assertNotNull(updated);
        assertEquals(NAME, updated.getName());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void testUpdateVendorNotFound() {
        when(vendorRepository.existsById(VENDOR_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.updateVendor(VENDOR_ID, new VendorDTO()));
        verify(vendorRepository, never()).save(any(Vendor.class));
    }

    @Test
    void testPatchVendor() {
        Vendor original = new Vendor();
        original.setId(VENDOR_ID);
        original.setName(NAME + "_original");
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(original));

        Vendor changed = new Vendor();
        changed.setName(NAME);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(changed);

        VendorDTO inputDTO = new VendorDTO();
        inputDTO.setName(NAME);

        VendorDTO patched = service.patchVendor(VENDOR_ID, inputDTO);
        assertNotNull(patched);
        assertEquals(NAME, patched.getName());
        verify(vendorRepository).findById(VENDOR_ID);
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void testPatchVendorNotFound() {
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.patchVendor(VENDOR_ID, new VendorDTO()));
        verify(vendorRepository, never()).save(any(Vendor.class));
    }

    @Test
    void testDeleteVendor() {
        when(vendorRepository.existsById(VENDOR_ID)).thenReturn(true);

        service.deleteVendor(VENDOR_ID);
        verify(vendorRepository).deleteById(VENDOR_ID);
    }

    @Test
    void testDeleteVendorNotFound() {
        when(vendorRepository.existsById(VENDOR_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteVendor(VENDOR_ID));
        verify(vendorRepository, never()).deleteById(VENDOR_ID);
    }
}
