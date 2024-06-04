package com.t3h.topcv.dto;

import com.t3h.topcv.entity.candidate.Skill;
import lombok.Data;

import java.util.List;

@Data
public class SkillResponse {

    private String message;

    private List <Skill> data;
}
