package com.t3h.topcv.controller.Job;

import com.t3h.topcv.dto.*;
import com.t3h.topcv.entity.job.*;
import com.t3h.topcv.repository.Company.AddressCompanyRepository;
import com.t3h.topcv.repository.Job.*;
import com.t3h.topcv.repository.SalaryRepository;
import com.t3h.topcv.service.CompanyService;
import com.t3h.topcv.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class JobController {

    private final JobService jobService;

    private final SalaryJobRepository salaryJobRepository;

    private final AddressCompanyRepository addressCompanyRepository;

    private final TypeJobRepository typeJobRepository;

    private final FieldJobRepository fieldJobRepository;

    private final LevelJobRepository levelJobRepository;

    private final CompanyService companyService;

    private final LevelJobDetailRepository levelJobDetailRepository;

    private final SalaryRepository salaryRepository;

    @Autowired
    public JobController(JobService jobService, SalaryJobRepository salaryJobRepository, AddressCompanyRepository addressCompanyRepository, TypeJobRepository typeJobRepository, FieldJobRepository fieldJobRepository, LevelJobRepository levelJobRepository, CompanyService companyService, LevelJobDetailRepository levelJobDetailRepository, SalaryRepository salaryRepository) {
        this.jobService = jobService;
        this.salaryJobRepository = salaryJobRepository;
        this.addressCompanyRepository = addressCompanyRepository;
        this.typeJobRepository = typeJobRepository;
        this.fieldJobRepository = fieldJobRepository;
        this.levelJobRepository = levelJobRepository;
        this.companyService = companyService;
        this.levelJobDetailRepository = levelJobDetailRepository;
        this.salaryRepository = salaryRepository;
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
        try {
            // Get the current date and format it to a string
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String now = formatter.format(new Date());

            // Call a method from the JobService to get all live jobs
            List<Job> allJobs = jobService.getLiveJobs();
            if (allJobs == null) {
                throw new RuntimeException("Error retrieving jobs from the service");
            }

            // Filter the jobs based on the created_at date
            List<Job> filteredJobs = allJobs.stream()
                    .filter(job -> {
                        if (job.getCreatedAt() != null) {
                            try {
                                Date jobDate = formatter.parse(job.getCreatedAt());
                                return formatter.format(jobDate).compareTo(now) >= 0;
                            } catch (ParseException e) {
                                // Handle the exception
                                return false;
                            }
                        } else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());

            // Create the response
            JobResponse response = new JobResponse();
            response.setMessage("success");
            response.setData(filteredJobs);
            response.setResult(allJobs);

            // Return the response
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // get job by id
    @GetMapping("/jobs/detail/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);

        SingleResponse response = new SingleResponse();
        response.setMessage("success");
        response.setData(job);

        return ResponseEntity.ok(response);
    }

    // create job
    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(@RequestBody Job job) {

        jobService.createJob(job);

        return ResponseEntity.ok(job);
    }

    // delete job
    @DeleteMapping("/jobs/delete/{id}")
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

    @GetMapping("/jobs/getJobAppliedCandidatesById/{id}")
    public ResponseEntity<?> getJobAppliedCandidateById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<Job_Candidates> result = jobService.getJobAppliedCandidatesById(id, userDetails.getUsername());

            JobCandidateResponse response = new JobCandidateResponse();
            response.setMessage("success");
            response.setData(result);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/jobs/getJobsForCompany")
    public ResponseEntity<?> getJobsForCompany(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestParam(defaultValue = "1") String status,
                                                @RequestParam(required = false) String key)
    {
        try {
            List<Job> result = jobService.getJobsForCompany(userDetails.getUsername(), status, key);

            JobResponse response = new JobResponse();
            response.setMessage("success");
            response.setData(result);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/jobs/getSalaryJob/{id}")
    public ResponseEntity<?> getSalaryJobs(@PathVariable Long id) {
        try {
            Salary_Jobs result = salaryJobRepository.findById(id).orElse(null);

            if (result == null) {
                return ResponseEntity.notFound().build();
            }

            SalaryJobResponse response = new SalaryJobResponse();
            response.setId(result.getId());
            response.setJobName(result.getJob().getTitle());
            response.setSalaryName(result.getSalary_id().getName());


            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/jobs/edit/{id}")
    public ResponseEntity<?> editJob(@PathVariable Long id, @RequestBody Map<String, String> job) {
        try {
            Job jobTemp = jobService.getJobById(id);
            if (jobTemp == null) {
                return ResponseEntity.notFound().build();
            }
            System.out.println(job.get("title"));

            jobTemp.setTitle(job.get("title"));
            jobTemp.setDescription(job.get("description"));
            jobTemp.setRequirement(job.get("requirements"));
//            jobTemp.setSalary(job.get("salary"));
            jobTemp.setCreatedAt(job.get("created_at") != null ? job.get("created_at") : null);
            jobTemp.setExpiredAt(job.get("expire_at") != null ? job.get("expire_at") : null);
            jobTemp.setAddressCompanyId(job.get("address_company_id") != null ? addressCompanyRepository.findById(Long.parseLong(job.get("address_company_id"))).orElse(null) : null);

            // Retrieve the Type_Jobs object
            if (job.get("typejob_id") != null) {
                try {
                    Long typeJobId = Long.parseLong(job.get("typejob_id"));
                    Type_Jobs typeJob = typeJobRepository.findById(typeJobId).orElse(null);
                    if (typeJob != null) {
                        Type_Jobs typeJobTemp = new Type_Jobs();
                        typeJobTemp.setJob_id(jobTemp);
                        typeJobTemp.setFieldJob(fieldJobRepository.findById(typeJobId).orElse(null));
                        typeJobRepository.save(typeJob);
                    }
                } catch (NumberFormatException e) {
                    // Handle the exception
                    return ResponseEntity.status(400).body("Invalid typejob_id");
                }
            }
            // Retrieve the Level_Job object
            if (job.get("leveljob_id") != null) {
                try {
                    Long levelJobId = Long.parseLong(job.get("leveljob_id"));
                    Level_Job levelJob = levelJobRepository.findById(levelJobId).orElse(null);
                    if (levelJob != null) {
                        Level_Job_Detail levelJobTemp = new Level_Job_Detail(levelJob, jobTemp);
                        levelJobDetailRepository.save(levelJobTemp);
                    }
                } catch (NumberFormatException e) {
                    // Handle the exception
                    return ResponseEntity.status(400).body("Invalid leveljob_id");
                }
            }

            // Add salary job
            if (job.get("salary") != null) {
                try {
                    Integer salaryId = Integer.parseInt(job.get("salary"));
                    Salary_Jobs salaryJob = salaryJobRepository.findByJob(jobTemp);
                    if (salaryJob != null) {
                        salaryJob.setSalary_id(salaryRepository.findById(salaryId).orElse(null));
                        salaryJobRepository.save(salaryJob);
                    } else
                    {
                        Salary_Jobs salaryJobTemp = new Salary_Jobs();
                        salaryJobTemp.setJob(jobTemp);
                        salaryJobTemp.setSalary_id(salaryRepository.findById(salaryId).orElse(null));
                        salaryJobRepository.save(salaryJobTemp);
                    }

                } catch (NumberFormatException e) {
                    // Handle the exception
                    return ResponseEntity.status(400).body("Invalid salary_id");
                }
            }

            jobService.updateJob(id,jobTemp);

            SingleResponse response = new SingleResponse();
            response.setMessage("Update job successfully");
            response.setData(jobTemp);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/jobs/create/{id}")
    public ResponseEntity<?> createJob(@PathVariable Long id, @RequestBody Map<String, String> job) {
        try {
            Job jobTemp = new Job();
            jobTemp.setTitle(job.get("title"));
            jobTemp.setDescription(job.get("description"));
            jobTemp.setRequirement(job.get("requirements"));
            jobTemp.setSalary(job.get("salary"));
            jobTemp.setExpiredAt(job.get("expire_at") != null ? job.get("expire_at") : null);
            jobTemp.setAddressCompanyId(job.get("address_company_id") != null ? addressCompanyRepository.findById(Long.parseLong(job.get("address_company_id"))).orElse(null) : null);
            jobTemp.setStatus("1");
            jobTemp.setCompanyId(companyService.findById(id));

            // Retrieve the Type_Jobs object
            if (job.get("typejob_id") != null) {
                try {
                    Long typeJobId = Long.parseLong(job.get("typejob_id"));
                    Type_Jobs typeJob = typeJobRepository.findById(typeJobId).orElse(null);
                    if (typeJob != null) {
                        Type_Jobs typeJobTemp = new Type_Jobs();
                        typeJobTemp.setJob_id(jobTemp);
                        typeJobTemp.setFieldJob(fieldJobRepository.findById(typeJobId).orElse(null));
                        typeJobRepository.save(typeJob);
                    }
                } catch (NumberFormatException e) {
                    // Handle the exception
                    return ResponseEntity.status(400).body("Invalid typejob_id");
                }
            }
            // Retrieve the Level_Job object
            if (job.get("leveljob_id") != null) {
                try {
                    Long levelJobId = Long.parseLong(job.get("leveljob_id"));
                    Level_Job levelJob = levelJobRepository.findById(levelJobId).orElse(null);
                    if (levelJob != null) {
                        Level_Job_Detail levelJobTemp = new Level_Job_Detail(levelJob, jobTemp);
                        levelJobDetailRepository.save(levelJobTemp);
                    }
                } catch (NumberFormatException e) {
                    // Handle the exception
                    return ResponseEntity.status(400).body("Invalid leveljob_id");
                }
            }

            // Add salary job
            if (job.get("salary") != null) {
                try {
                    Integer salaryId = Integer.parseInt(job.get("salary"));
                    Salary_Jobs salaryJobTemp = new Salary_Jobs();
                    salaryJobTemp.setJob(jobTemp);
                    salaryJobTemp.setSalary_id(salaryRepository.findById(salaryId).orElse(null));
                    salaryJobRepository.save(salaryJobTemp);

                } catch (NumberFormatException e) {
                    // Handle the exception
                    return ResponseEntity.status(400).body("Invalid salary_id");
                }
            }

            jobService.createJob(jobTemp);

            return ResponseEntity.ok().body("Job created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
