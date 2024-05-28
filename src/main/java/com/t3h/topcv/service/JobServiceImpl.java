package com.t3h.topcv.service;

import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;

    private final CompanyService companyService;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Job> getAllJob() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void createJob(Job job) {

        Job jobTemp = new Job();

        jobTemp.setTitle(job.getTitle());
        jobTemp.setCreatedAt(new Date());
        jobTemp.setUpdatedAt(new Date());
        jobTemp.setExpiredAt(job.getExpiredAt());
        jobTemp.setSalary(job.getSalary());
        jobTemp.setDescription(job.getDescription());
        jobTemp.setJobCandidates(job.getJobCandidates());
        jobTemp.setLevelJobId(job.getLevelJobId());
        jobTemp.setSalaryJobs(job.getSalaryJobs());
        jobTemp.setRequirement(job.getRequirement());
        jobTemp.setStatus(job.getStatus());
        jobTemp.setTypeJobs(job.getTypeJobs());
        jobTemp.setAddressCompanyId(job.getAddressCompanyId());
        jobTemp.setCompanyId(companyService.findById(job.getCompanyId().getId()));;

        jobRepository.save(jobTemp);
    }

    @Override
    @Transactional
    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id).orElse(null);

        if (job == null) {
            return;
        }

        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> getJobByCompanyId(Long companyId) {
        return jobRepository.findAllByCompanyId(companyService.findById(companyId));
    }

    @Override
    @Transactional
    public Job updateJob(Long id, Job job) {

        Job jobTemp = jobRepository.findById(id).orElse(null);

        if (jobTemp == null) {
            return null;
        }

        jobTemp.setTitle(job.getTitle());
        jobTemp.setUpdatedAt(new Date());
        jobTemp.setExpiredAt(job.getExpiredAt());
        jobTemp.setSalary(job.getSalary());
        jobTemp.setDescription(job.getDescription());
        jobTemp.setJobCandidates(job.getJobCandidates());
        jobTemp.setLevelJobId(job.getLevelJobId());
        jobTemp.setSalaryJobs(job.getSalaryJobs());
        jobTemp.setRequirement(job.getRequirement());
        jobTemp.setStatus(job.getStatus());
        jobTemp.setTypeJobs(job.getTypeJobs());
        jobTemp.setAddressCompanyId(job.getAddressCompanyId());
        jobTemp.setCompanyId(job.getCompanyId());;

        return jobRepository.save(jobTemp);

    }

    @Override
    public List<Job> getHomepageJob() {
        // get 6 job
        return jobRepository.findTop6ByOrderByIdDesc();
    }

    @Override
    public List<Job> getLiveJobs() {
        return jobRepository.findAllByStatus("1");
    }

    @Override
    public List<Job> getAllPageJob(int page) {
        // Apply pagination: limit 6, offset based on page number
        Pageable pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Execute the query
        List<Job> result = jobRepository.findAll(pageable).toList();

        // Return the result
        return result;
    }
}
