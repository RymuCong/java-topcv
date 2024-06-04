package com.t3h.topcv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryJobResponse {

    private Long id;
    private String jobName;
    private String salaryName;
}
