package com.t3h.topcv.controller.Job;

import com.t3h.topcv.dto.*;
import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.entity.job.Job_Candidates;
import com.t3h.topcv.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/jobs/getNewJobs")
    public ResponseEntity<?> findAllNewJobs() {
        // Get the current date and format it to a string
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String now = formatter.format(new Date());

        // Call a method from the JobService to get all live jobs
        List<Job> allJobs = jobService.getLiveJobs();

        // Filter the jobs based on the created_at date
        List<Job> filteredJobs = allJobs.stream()
                .filter(job -> formatter.format(job.getCreatedAt()).compareTo(now) >= 0)
                .collect(Collectors.toList());

        // Create the response
        JobResponse response = new JobResponse();
        response.setMessage("success");
        response.setData(filteredJobs);
        response.setResult(allJobs);

        // Return the response
        return ResponseEntity.ok(response);
    }

    // get job by id
    @GetMapping("/jobs/detail/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);

        SingleResponse response = new SingleResponse();

        if (job == null) {
            response.setMessage("Job not found");
        } else {
            response.setMessage("success");
            response.setData(job);
        }
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

    @GetMapping("/jobs/searchJob")
    public ResponseEntity<?> searchJob(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String leveljob,
            @RequestParam(required = false) String salary) {
        try {
            List<Job> result = jobService.searchJob(name, location, leveljob, salary);
            JobResponse response = new JobResponse();
            response.setMessage("success");
            response.setData(result);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/jobs/applyJob")
    public ResponseEntity<?> applyJob(@RequestBody ApplyJobResponse applyJobDto) {
        try {
            Job_Candidates result = jobService.applyJob(applyJobDto);
            result.setStatus("1");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/jobs/getJobAppliedCandidates")
    public ResponseEntity<?> getJobAppliedCandidates(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<Job_Candidates> result = jobService.getJobAppliedCandidates(userDetails.getUsername());

            JobCandidateResponse response = new JobCandidateResponse();
            response.setMessage("success");
            response.setData(result);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/jobs/getJobsForCompany")
    public ResponseEntity<?> getJobsForCompany(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(defaultValue = "1") String status) {
        try {
            List<Job> result = jobService.getJobsForCompany(userDetails.getUsername(), status);

            JobResponse response = new JobResponse();
            response.setMessage("success");
            response.setData(result);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
