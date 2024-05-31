package com.t3h.topcv.dto;

import com.t3h.topcv.entity.job.Job_Candidates;
import lombok.Data;

import java.util.List;

@Data
public class JobCandidateResponse {

    private String message;
    private List<Job_Candidates> data;
}
