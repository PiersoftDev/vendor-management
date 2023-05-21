package com.piersoft.vm.service;

import com.piersoft.vm.persistence.entities.VendorKYC;
import com.piersoft.vm.request.dto.OnboardVendorDTO;

import java.util.List;

public interface VendorService {

    Integer onboardVendor(OnboardVendorDTO onboardVendorDTO);

    List<VendorKYC> listAllVendors();
}
