package com.t3h.topcv.dto;

import lombok.Data;

@Data
public class ApplyJobResponse {

    private String message;
    private Long candidate_id;
    private Long job_id;
    private String content;
    private String cv_url;
}
