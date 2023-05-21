package com.piersoft.vm.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueGSTAndPAN", columnNames = { "gst", "pan" }) })
public class VendorKYC {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String gst;
    private String aadhaar;
    private String pan;
    private String pocName;
    private String pocWhatsappNo;
    private String pocEmail;
    private String businessName;
    private String address;
    private String state;

}
