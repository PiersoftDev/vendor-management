package com.piersoft.vm.request.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardVendorDTO {

    @NotNull(message = "GST Number cannot be null")
    private String gst;
    private String aadhaar;
    private String pan;
    private String pocName;
    private String pocWhatsappNo;
    private String pocEmail;
}
