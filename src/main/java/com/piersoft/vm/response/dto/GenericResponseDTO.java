package com.piersoft.vm.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Data
public class GenericResponseDTO {

    private HttpStatus status_code;
    private String message_code;
    private boolean success;
    private List<String> errors;
    private Object data;
}
