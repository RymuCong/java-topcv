package com.t3h.topcv.dto;

import com.t3h.topcv.entity.candidate.Experience;
import lombok.Data;

import java.util.List;

@Data
public class ExperienceResponse {

    private String message;

    private List<Experience> data;
}
