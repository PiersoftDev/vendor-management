package com.piersoft.vm.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/vm")
public class controller {

    @PostMapping("/onboard")
    public ResponseEntity<String> onboardVendor(){
        return ResponseEntity.ok("Successfully onboarded vendor");
    }
}
