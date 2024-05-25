package com.t3h.topcv.service;

import com.t3h.topcv.entity.job.Job;

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

}
