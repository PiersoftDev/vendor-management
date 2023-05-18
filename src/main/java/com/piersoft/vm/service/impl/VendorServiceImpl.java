package com.piersoft.vm.service.impl;

import com.piersoft.vm.ApiClientUtil;
import com.piersoft.vm.SurepassUtil;
import com.piersoft.vm.persistence.entities.VendorKYC;
import com.piersoft.vm.persistence.repositories.VendorKYCRepository;
import com.piersoft.vm.request.dto.OnboardVendorDTO;
import com.piersoft.vm.request.dto.mapper.OnboardVendorRequestMapper;
import com.piersoft.vm.response.dto.GSTByPanResponseDTO;
import com.piersoft.vm.response.dto.GSTResponseDTO;
import com.piersoft.vm.response.dto.PanGSTDTO;
import com.piersoft.vm.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VendorServiceImpl implements VendorService {

    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    private OnboardVendorRequestMapper onboardVendorRequestMapper;

    @Autowired
    private VendorKYCRepository vendorKYCRepository;

    @Autowired
    private ApiClientUtil apiClientUtil;

    @Autowired
    private SurepassUtil surepassUtil;


    @Override
    public Integer onboardVendor(OnboardVendorDTO onboardVendorDTO) {
        String panNo = onboardVendorDTO.getPan();
        GSTByPanResponseDTO gstByPanResponseDTO = surepassUtil.fetchPANDetails(panNo);
        if(gstByPanResponseDTO != null){
            List<PanGSTDTO> gstList = gstByPanResponseDTO.getData().getGstin_list();
            Optional<PanGSTDTO> optionalGST = gstList.stream().filter(gst -> gst.getGstin().equalsIgnoreCase(onboardVendorDTO.getGst()) && gst.getActive_status().equalsIgnoreCase("Active")).findAny();
            if(optionalGST.isPresent()){
                GSTResponseDTO gstResponseDTO = surepassUtil.fetchGSTDetails(onboardVendorDTO.getGst());
                VendorKYC vendorKYC = onboardVendorRequestMapper.requestToEntity(onboardVendorDTO);
                vendorKYC.setAddress(gstResponseDTO.getData().getAddress());
                vendorKYC.setBusinessName(gstResponseDTO.getData().getBusiness_name());
                Long start = System.currentTimeMillis();
                vendorKYCRepository.save(vendorKYC);
                Long end = System.currentTimeMillis();
                logger.info("Time taken to save the vendor details:"+(end-start));
            }
            return gstList.size();
        }
        return 0;
    }
}
