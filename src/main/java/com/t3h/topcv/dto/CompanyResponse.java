package com.t3h.topcv.dto;

import com.t3h.topcv.entity.company.Company;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResponse {
        private String message;
        private List<Company> data;
        // getters and setters
}
