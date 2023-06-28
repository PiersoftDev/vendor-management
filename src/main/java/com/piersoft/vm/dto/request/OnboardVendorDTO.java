package com.piersoft.vm.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardVendorDTO {

    @NotEmpty(message = "User Id cannot be null or empty")
    private String userId;
    @NotEmpty(message = "PAN Number cannot be null or empty")
    private String pan;
    @NotEmpty(message = "GST Number cannot be null or empty")
    private String gst;
    @NotEmpty(message = "Bank Account cannot be null or empty")
    private String accNo;
    @NotEmpty(message = "Bank IFSC code cannot be null or empty")
    private String ifscCode;
    @NotEmpty(message = "Bank Account Type cannot be null or empty")
    private String accountType;
    @NotEmpty(message = "Point of contact name cannot be null or empty")
    private String name;
    @NotEmpty(message = "Point of contact email cannot be null or empty")
    private String email;
    @NotEmpty(message = "Point of contact country code cannot be null or empty")
    private String countryCode;
    @NotEmpty(message = "Point of contact whatsapp number cannot be null or empty")
    private String whatsappNumber;
    @NotEmpty(message = "Point of contact aadhaar number cannot be null or empty")
    private String aadhaarNumber;
    @NotEmpty(message = "Pan Card URL cannot be null or empty")
    private String panCardURL;
    @NotEmpty(message = "GST Certificate URL cannot be null or empty")
    private String gstCertificateURL;
    @NotEmpty(message = "Aadhaar card URL cannot be null or empty")
    private String aadhaarCardURL;
    @NotEmpty(message = "Cancelled cheque URL cannot be null or empty")
    private String cancelledChequeURL;
    @NotEmpty(message = "Partnership deed URL cannot be null or empty")
    private String partnershipDeedURL;
}
