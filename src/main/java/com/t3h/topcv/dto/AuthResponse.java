package com.t3h.topcv.dto;

import com.t3h.topcv.entity.job.Job_Candidates;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private Data data;

    // getters and setters

    public AuthResponse(String message, Data data) {
        this.message = message;
        this.data = data;
    }

    @lombok.Data
    public static class Data {
        private int status;
        private int role;
        private String token_access;
        private String token;

        // getters and setters
    }

    @lombok.Data
    public static class JobCandidateResponse {

        private String message;

        private List<Job_Candidates> data;
    }
}