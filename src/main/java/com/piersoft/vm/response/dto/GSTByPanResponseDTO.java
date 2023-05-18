package com.piersoft.vm.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTByPanResponseDTO {

    private String status_code;

    private GSTListByPanDTO data;

    private String message_code;

    private String success;

    private String message;
}
