package com.piersoft.vm.service;

import com.piersoft.vm.persistence.entities.VendorKYC;
import com.piersoft.vm.dto.request.OnboardVendorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VendorService {

    Integer onboardVendor(OnboardVendorDTO onboardVendorDTO);

    List<VendorKYC> listAllVendors();

    void onboardAllVendorsByPAN(String panNo);

    String uploadVendorDocument(String userId, String docType, MultipartFile multipartFile);
}
