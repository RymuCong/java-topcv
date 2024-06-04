package com.t3h.topcv.service;

import com.t3h.topcv.dto.ApplyJobResponse;
import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.entity.job.Job_Candidates;
import com.t3h.topcv.entity.job.Level_Job;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface JobService {

    // crud job
    // get all job
    List<Job> getAllJob();

    // get job by id
    Job getJobById(Long id);

    // create job
    void createJob(Job job);

    // delete job
    void deleteJob(Long id);

    // get job by company id
    List<Job> getJobByCompanyId(Long companyId);

    // update job
    Job updateJob(Long id, Job job);

    // get homepage job
    List<Job> getHomepageJob();

    List<Job> getLiveJobs();

    List<Job> getAllPageJob(int page);

    List<Job> searchJob(String name, String location, String leveljob, String salary);

    Job_Candidates applyJob(ApplyJobResponse applyJobResponse);

    List<Job_Candidates> getJobAppliedCandidates(String username);

    List<Job> getJobsForCompany(String username, String status, String key);

    void save(Job job);

    List<Job_Candidates> getJobAppliedCandidatesById(Long id, String username);

    List<Job_Candidates> getCandidatesbyIdJob(Long id);

    void updateInterview(Long id, String interviewDay, String interviewAddress, String nameCompany, String emailCompany);

    void cancelCandidate(Long idApply, String nameCompany);
}
