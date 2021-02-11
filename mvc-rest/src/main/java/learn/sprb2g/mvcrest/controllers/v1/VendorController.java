package learn.sprb2g.mvcrest.controllers.v1;

import learn.sprb2g.mvcrest.api.v1.model.VendorDTO;
import learn.sprb2g.mvcrest.api.v1.model.VendorListDTO;
import learn.sprb2g.mvcrest.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO listAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getById(@PathVariable Long vendorId) {
        return vendorService.getVendorById(vendorId)
                .map(vendorDTO -> new ResponseEntity<>(vendorDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO create(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO update(@PathVariable Long vendorId, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(vendorId, vendorDTO);
    }

    @PatchMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patch(@PathVariable Long vendorId, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(vendorId, vendorDTO);
    }

    @DeleteMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long vendorId) {
        vendorService.deleteVendor(vendorId);
    }
}
