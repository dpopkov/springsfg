package learn.sprb2g.mvcrest.api.v1.mapper;

import learn.sprb2g.mvcrest.api.v1.model.VendorDTO;
import learn.sprb2g.mvcrest.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(target = "vendorUrl", ignore = true)
    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
