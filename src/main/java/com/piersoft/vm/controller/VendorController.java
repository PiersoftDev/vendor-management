package com.piersoft.vm.controller;


import com.piersoft.vm.SurepassUtil;
import com.piersoft.vm.dto.request.OnboardVendorDTO;
import com.piersoft.vm.persistence.entities.Vendor;
import com.piersoft.vm.dto.response.GSTByPanResponseDTO;
import com.piersoft.vm.dto.response.GenericResponseDTO;
import com.piersoft.vm.service.VendorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vm")
@CrossOrigin
public class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @Autowired
    private SurepassUtil surepassUtil;



    @ApiOperation(value = "Onboard a vendor", notes = "Returns vendor onboarding status", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully onboarded a vendor"),
            @ApiResponse(code = 404, message = "Filed to onboard a vendor")
    })
    @PostMapping("/onboard")
    public ResponseEntity<GenericResponseDTO> onboardVendor(@RequestBody OnboardVendorDTO onboardVendorDTO){
        logger.debug("Onboarding vendor");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<OnboardVendorDTO>> violationSet = validator.validate(onboardVendorDTO);
        if(!violationSet.isEmpty()){
            logger.error("Request validation failed for fields");
            GenericResponseDTO errorResponse = GenericResponseDTO.builder().status_code(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .errors(violationSet.stream().map(v -> v.getMessage()).collect(Collectors.toList()))
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Integer totalGSTNosWithGivenPanNo = vendorService.onboardVendor(onboardVendorDTO);
        logger.debug("Successfully onboarded vendor");
        return ResponseEntity.ok(GenericResponseDTO.builder().status_code(HttpStatus.OK).success(true).data("Successfully Onboarded Vendor").message_code("Multiple GSTs found under PAN, would you like to onboard them all ?").build());
    }

    @ApiOperation(value = "Onboard a vendor", notes = "Returns vendor onboarding status", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully onboarded a vendor"),
            @ApiResponse(code = 404, message = "Filed to onboard a vendor")
    })
    @PostMapping("/onboard/{panNo}")
    public ResponseEntity<GenericResponseDTO> onboardAllVendorsByPAN(@PathVariable(required = true) String panNo){
        logger.debug("Onboarding all vendors for PAN No:"+panNo);
        vendorService.onboardAllVendorsByPAN(panNo);
        logger.debug("Onboarded all vendors for PAN No:"+panNo);
        return ResponseEntity.ok(GenericResponseDTO.builder().status_code(HttpStatus.OK).success(true).data("Successfully Onboarded Vendor").message_code("Multiple GSTs found under PAN, would you like to onboard them all ?").build());
    }

    @ApiOperation(value = "Fetch all the vendors", notes = "Returns a list of vendors", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns all the vendors"),
            @ApiResponse(code = 404, message = "Not found - no vendors found")
    })
    @GetMapping("/list")
    public ResponseEntity<List<Vendor>> listAllVendors(){
        logger.debug("Listing all vendors");
        return ResponseEntity.ok(vendorService.listAllVendors());
    }


    @ApiOperation(value = "Get all GSTs for a PAN Number", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns all GST Number for the PAN Number"),
            @ApiResponse(code = 404, message = "Invalid PAN Number")
    })
    @PostMapping("/fetchGSTsForPAN/{panNumber}")
    public ResponseEntity<GSTByPanResponseDTO> fetchGSTsForPANNumber(@PathVariable String panNumber){
        logger.debug("Fetching GSTs for the PAN Number::{}", panNumber);
        GSTByPanResponseDTO gstByPanResponseDTO = surepassUtil.fetchPANDetails(panNumber);
        logger.debug("Done fetching GSTs for the PAN Number::{}", panNumber);
        return ResponseEntity.ok(gstByPanResponseDTO);
    }

    @ApiOperation(value = "Upload vendor document", notes = "Returns vendor document upload status", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully uploaded vendor document"),
            @ApiResponse(code = 404, message = "Failed to upload vendor document")
    })
    @PostMapping("/upload/{docType}/{userId}")
    public ResponseEntity<String> uploadDocument(@PathVariable String userId, @PathVariable String docType, @RequestPart("image") MultipartFile vendorDocument) {
        logger.debug("Uploading ::{} document for user::{}",docType, userId);
        String publicURL = vendorService.uploadVendorDocument(userId,docType, vendorDocument);
        logger.debug("Successfully uploaded ::{} document for user::{}",docType, userId);
        return ResponseEntity.ok(publicURL);
    }

}
