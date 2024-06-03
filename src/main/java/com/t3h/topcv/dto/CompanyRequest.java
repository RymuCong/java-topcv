package com.t3h.topcv.dto;

import lombok.Data;

@Data
public class CompanyRequest {

    private String name;
    private String description;
    private String logo;
    private String website;
    private String link_facebook;
    private String phone;
    private String email;
    private String policy;
    private Integer size;
    private Long TypeCompanyId;
}
