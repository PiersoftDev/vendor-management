package com.piersoft.vm.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDTO extends GenericResponseDTO {

    private String successMsg;

}
