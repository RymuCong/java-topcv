package com.t3h.topcv.service;

import com.t3h.topcv.dto.ApplyJobResponse;
import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.entity.job.Job_Candidates;

import java.util.List;

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
}
