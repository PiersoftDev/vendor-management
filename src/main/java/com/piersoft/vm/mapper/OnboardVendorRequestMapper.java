package com.piersoft.vm.mapper;

import com.piersoft.vm.dto.request.OnboardVendorDTO;
import com.piersoft.vm.persistence.entities.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OnboardVendorRequestMapper {

    Vendor requestToEntity(OnboardVendorDTO onboardVendorDTO);

    OnboardVendorDTO entityToRequest(Vendor vendorKYC);
}
