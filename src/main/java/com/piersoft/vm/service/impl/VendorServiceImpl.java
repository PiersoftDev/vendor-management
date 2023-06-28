package com.piersoft.vm.service.impl;

import com.piersoft.vm.ApiClientUtil;
import com.piersoft.vm.SurepassUtil;
import com.piersoft.vm.WhatsappNotificationUtil;
import com.piersoft.vm.dto.request.OnboardVendorDTO;
import com.piersoft.vm.persistence.entities.Vendor;
import com.piersoft.vm.persistence.repositories.VendorKYCRepository;
import com.piersoft.vm.mapper.OnboardVendorRequestMapper;
import com.piersoft.vm.dto.response.GSTByPanResponseDTO;
import com.piersoft.vm.dto.response.GSTResponseDTO;
import com.piersoft.vm.dto.response.PanGSTDTO;
import com.piersoft.vm.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

    @Autowired
    private WhatsappNotificationUtil whatsappNotificationUtil;

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.endpoint}")
    private String endpoint;


    @Override
    public Integer onboardVendor(OnboardVendorDTO onboardVendorDTO) {
        String panNo = onboardVendorDTO.getPan();
        GSTByPanResponseDTO gstByPanResponseDTO = surepassUtil.fetchPANDetails(panNo);
        if(gstByPanResponseDTO != null){
            List<PanGSTDTO> gstList = gstByPanResponseDTO.getData().getGstin_list();
            Optional<PanGSTDTO> optionalGST = gstList.stream().filter(gst -> gst.getGstin().equalsIgnoreCase(onboardVendorDTO.getGst()) && gst.getActive_status().equalsIgnoreCase("Active")).findAny();
            if(optionalGST.isPresent()){
                GSTResponseDTO gstResponseDTO = surepassUtil.fetchGSTDetails(onboardVendorDTO.getGst());
                Vendor vendorKYC = onboardVendorRequestMapper.requestToEntity(onboardVendorDTO);
                vendorKYC.setAddress(gstResponseDTO.getData().getAddress());
                vendorKYC.setBusinessName(gstResponseDTO.getData().getBusiness_name());
                vendorKYC.setState(optionalGST.get().getState());
                Long start = System.currentTimeMillis();
                vendorKYCRepository.save(vendorKYC);
                Long end = System.currentTimeMillis();
                logger.info("Time taken to save the vendor details:"+(end-start));
                //whatsappNotificationUtil.sendWelcomeMessage();
            }
            return gstList.size();
        }
        return 0;
    }

    @Override
    public List<Vendor> listAllVendors() {
        return (List<Vendor>)vendorKYCRepository.findAll();
    }

    @Override
    public void onboardAllVendorsByPAN(String panNo) {
        GSTByPanResponseDTO gstByPanResponseDTO = surepassUtil.fetchPANDetails(panNo);
        if(gstByPanResponseDTO != null) {
            List<PanGSTDTO> gstList = gstByPanResponseDTO.getData().getGstin_list();
            if (!gstList.isEmpty()) {
                for (PanGSTDTO gst : gstList) {
                    if (!gst.getActive_status().equalsIgnoreCase("Active")) {
                        continue;
                    }
                    GSTResponseDTO gstResponseDTO = surepassUtil.fetchGSTDetails(gst.getGstin());
                    Vendor vendorKYC = Vendor.builder()
                            .address(gstResponseDTO.getData().getAddress())
                            .businessName(gstResponseDTO.getData().getBusiness_name())
                            .state(gst.getState())
                            .pan(panNo)
                            .gst(gst.getGstin())
                            .build();
                    vendorKYCRepository.save(vendorKYC);
                }

            }
        }
    }

    @Override
    public String uploadVendorDocument(String userId, String docType, MultipartFile imageFile) {
        try {

            String fileKey = userId+"/"+docType;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .acl("public-read")
                    .build();
            InputStream is = new ByteArrayInputStream(imageFile.getBytes());
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(is, imageFile.getSize()));
            String publicUrl = endpoint+fileKey;
            return publicUrl;

        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
        return null;
    }
}
