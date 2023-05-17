package com.piersoft.vm.controller;


import com.piersoft.vm.request.dto.OnboardVendorDTO;
import com.piersoft.vm.response.dto.ErrorResponseDTO;
import com.piersoft.vm.response.dto.GenericResponseDTO;
import com.piersoft.vm.response.dto.SuccessResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/onboard")
    public ResponseEntity<GenericResponseDTO> onboardVendor(@RequestBody OnboardVendorDTO onboardVendorDTO){
        logger.debug("Onboarding vendor");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<OnboardVendorDTO>> violationSet = validator.validate(onboardVendorDTO);
        if(!violationSet.isEmpty()){
            logger.error("Request validation failed for fields");
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(violationSet.stream().map(v -> v.getMessage()).collect(Collectors.toList())));
        }
        logger.debug("Successfully onboarded vendor");
        return ResponseEntity.ok(new SuccessResponseDTO("Successfully onboarded vendor"));
    }
}
