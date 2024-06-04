package com.t3h.topcv.dto;

import com.t3h.topcv.entity.Salary;
import lombok.Data;

import java.util.List;

@Data
public class SalaryResponse {

    private String message;
    private List<Salary> data;
}
