package com.piersoft.vm.request.dto.mapper;

import com.piersoft.vm.persistence.entities.VendorKYC;
import com.piersoft.vm.request.dto.OnboardVendorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OnboardVendorRequestMapper {

    VendorKYC requestToEntity(OnboardVendorDTO onboardVendorDTO);

    OnboardVendorDTO entityToRequest(VendorKYC vendorKYC);
}
