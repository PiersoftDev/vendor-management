package com.piersoft.vm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTResponseDTO {
    private String status_code;

    private GSTDTO data;

    private String message_code;

    private String success;

    private String message;
}
