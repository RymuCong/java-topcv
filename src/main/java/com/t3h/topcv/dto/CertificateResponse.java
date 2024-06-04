package com.t3h.topcv.dto;

import com.t3h.topcv.entity.candidate.Certificate;
import lombok.Data;

import java.util.List;

@Data
public class CertificateResponse {

    private String message;

    private List <Certificate> data;
}
