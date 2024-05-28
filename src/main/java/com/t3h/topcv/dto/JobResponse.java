package com.t3h.topcv.dto;

import com.t3h.topcv.entity.job.Job;
import lombok.Data;

import java.util.List;

@Data
public class JobResponse {

    private String message;

    private List<Job> data;
}
