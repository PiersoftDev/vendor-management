package com.piersoft.vm.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanGSTDTO {

    private String active_status;

    private String state;

    private String gstin;

    private String state_code;
}
