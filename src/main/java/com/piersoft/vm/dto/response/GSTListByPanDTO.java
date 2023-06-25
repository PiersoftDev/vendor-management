package com.piersoft.vm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTListByPanDTO {

    private String pan_number;

    private List<PanGSTDTO> gstin_list;

    private String client_id;
}
