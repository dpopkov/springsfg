package learn.sprb2g.mvcrest.controllers.v1;

import learn.sprb2g.mvcrest.api.v1.model.VendorDTO;
import learn.sprb2g.mvcrest.services.VendorService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static learn.sprb2g.mvcrest.controllers.v1.VendorController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {VendorController.class})
class VendorControllerIT extends AbstractRestControllerTest {

    private static final Long VENDOR_ID = 42L;
    private static final String VENDOR_URL = BASE_URL + "/" + VENDOR_ID;
    private static final String VENDOR_NAME = "vendor-name";

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testListAllVendors() throws Exception {
        when(vendorService.getAllVendors()).thenReturn(List.of(new VendorDTO(), new VendorDTO()));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void testGetById() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName(VENDOR_NAME);
        when(vendorService.getVendorById(VENDOR_ID)).thenReturn(Optional.of(vendor));

        mockMvc.perform(get(VENDOR_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_NAME)));
    }

    @Test
    void testCreate() throws Exception {
        VendorDTO inputDto = new VendorDTO();
        inputDto.setName(VENDOR_NAME);

        VendorDTO createdDto = new VendorDTO();
        createdDto.setName(VENDOR_NAME);
        createdDto.setVendorUrl(VENDOR_URL);

        when(vendorService.createNewVendor(ArgumentMatchers.any(VendorDTO.class))).thenReturn(createdDto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }
}
