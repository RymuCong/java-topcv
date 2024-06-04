package com.t3h.topcv.dto;

import com.t3h.topcv.entity.job.Level_Job;
import lombok.Data;

import java.util.List;

@Data
public class LevelJobResponse {

    private String message;

    private List<Level_Job> data;
}
