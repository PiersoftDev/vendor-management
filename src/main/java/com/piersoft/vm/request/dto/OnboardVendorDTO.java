package com.piersoft.vm.request.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardVendorDTO {

    @NotEmpty(message = "GST Number cannot be null or empty")
    private String gst;
    @NotEmpty(message = "Aadhaar Number cannot be null or empty")
    private String aadhaar;
    @NotEmpty(message = "PAN Number cannot be null or empty")
    private String pan;
    @NotEmpty(message = "Point of contact name cannot be null or empty")
    private String pocName;
    @NotEmpty(message = "Point of whatsapp Number cannot be null or empty")
    private String pocWhatsappNo;
    @NotEmpty(message = "Point of contact email cannot be null or empty")
    private String pocEmail;
}
