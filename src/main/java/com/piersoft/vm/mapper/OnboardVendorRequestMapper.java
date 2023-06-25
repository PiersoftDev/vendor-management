package com.piersoft.vm.mapper;

import com.piersoft.vm.persistence.entities.VendorKYC;
import com.piersoft.vm.dto.request.OnboardVendorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OnboardVendorRequestMapper {

    VendorKYC requestToEntity(OnboardVendorDTO onboardVendorDTO);

    OnboardVendorDTO entityToRequest(VendorKYC vendorKYC);
}
