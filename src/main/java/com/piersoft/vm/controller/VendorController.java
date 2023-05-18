package com.piersoft.vm.controller;


import com.piersoft.vm.request.dto.OnboardVendorDTO;
import com.piersoft.vm.response.dto.GenericResponseDTO;
import com.piersoft.vm.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vm")
public class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

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
}
