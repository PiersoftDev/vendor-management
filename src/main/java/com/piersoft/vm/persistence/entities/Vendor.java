package com.piersoft.vm.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueGSTAndPAN", columnNames = { "userId","gst", "pan" }) })
public class Vendor {

    @Id
    private String userId;
    private String pan;
    private String gst;
    private String businessName;
    private String address;
    private String state;
    private String accNo;
    private String ifscCode;
    private String accountType;
    private String name;
    private String email;
    private String countryCode;
    private String whatsappNumber;
    private String aadhaarNumber;
    private String panCardURL;
    private String gstCertificateURL;
    private String aadhaarCardURL;
    private String cancelledChequeURL;
    private String partnershipDeedURL;
    private String status;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate updatedDate = LocalDate.now();

}
