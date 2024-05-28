package com.t3h.topcv.controller;

import com.t3h.topcv.dto.JobResponse;
import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.repository.JobRepository;
import com.t3h.topcv.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // get all job
    @GetMapping("/jobs")
    public ResponseEntity<?> getAllJob() {
        List<Job> jobs = jobService.getAllJob();

        return ResponseEntity.ok(jobs);
    }

    // get homepage job
    @GetMapping("/jobs/firstPage")
    public ResponseEntity<?> getHomepageJob() {
        List<Job> jobs = jobService.getHomepageJob();

        JobResponse response = new JobResponse();
        response.setMessage("success");
        response.setData(jobs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/jobs/getLiveJobs")
    public ResponseEntity<?> getLiveJobs() {
        List<Job> jobs = jobService.getLiveJobs();

        JobResponse response = new JobResponse();
        response.setMessage("success");
        response.setData(jobs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/jobs/getAllPaging")
    public ResponseEntity<?> getAllPageJob(@RequestParam(defaultValue = "0") int page) {
        List<Job> jobs = jobService.getAllPageJob(page);

        JobResponse response = new JobResponse();
        response.setMessage("success");
        response.setData(jobs);

        return ResponseEntity.ok(response);
    }

    // get job by id
    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);

        if (job == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(job);
    }

    // create job
    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(@RequestBody Job job) {

        jobService.createJob(job);

        return ResponseEntity.ok(job);
    }

    // delete job
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);

        return ResponseEntity.ok().build();
    }

    // update job
    @PutMapping("/jobs/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job jobTemp = jobService.updateJob(id, job);

        if (jobTemp == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(jobTemp);
    }
}
