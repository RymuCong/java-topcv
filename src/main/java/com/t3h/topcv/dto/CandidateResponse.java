package com.t3h.topcv.dto;

import com.t3h.topcv.entity.candidate.Candidate;
import lombok.Data;

import java.util.List;

@Data
public class CandidateResponse {

    private String message;
    private List<Candidate> data;
    // getters and setters
}
