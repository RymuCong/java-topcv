package com.t3h.topcv.dto;

import com.t3h.topcv.entity.candidate.Education;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class EducationResponse {

    private String message;

    List <Education> data;
}
