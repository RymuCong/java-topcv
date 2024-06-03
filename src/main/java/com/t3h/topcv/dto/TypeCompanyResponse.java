package com.t3h.topcv.dto;

import com.t3h.topcv.entity.company.Type_Company;
import lombok.Data;

import java.util.List;

@Data
public class TypeCompanyResponse {

    String message;
    List<Type_Company> data;
}
