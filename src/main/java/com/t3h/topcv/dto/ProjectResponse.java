package com.t3h.topcv.dto;

import com.t3h.topcv.entity.candidate.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {

    private String message;

    private List<Project> data;
}
