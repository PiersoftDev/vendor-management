package com.piersoft.vm.controller;


import com.piersoft.vm.persistence.entities.VendorKYC;
import com.piersoft.vm.request.dto.OnboardVendorDTO;
import com.piersoft.vm.response.dto.GenericResponseDTO;
import com.piersoft.vm.service.VendorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<VendorKYC>> listAllVendors(){
        logger.debug("Listing all vendors");
        return ResponseEntity.ok(vendorService.listAllVendors());
    }

}
