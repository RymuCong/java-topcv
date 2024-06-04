package com.t3h.topcv.service;

import com.t3h.topcv.dto.ApplyJobResponse;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.Salary;
import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.company.Address_Company;
import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.entity.job.*;
import com.t3h.topcv.repository.Job.*;
import com.t3h.topcv.service.Candidate.CandidateService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService{

    @PersistenceContext
    private EntityManager entityManager;

    private final JobRepository jobRepository;

    private final CompanyService companyService;

    private final CandidateService candidateService;

    private final JobCandidateRepository jobCandidatesRepository;

    private final SalaryJobRepository salaryJobRepository;

    private final TypeJobRepository typeJobRepository;

    private final JavaMailSender javaMailSender;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, CompanyService companyService, CandidateService candidateService, JobCandidateRepository jobCandidatesRepository, LevelJobRepository levelJobRepository, SalaryJobRepository salaryJobRepository, TypeJobRepository typeJobRepository, JavaMailSender javaMailSender) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
        this.candidateService = candidateService;
        this.jobCandidatesRepository = jobCandidatesRepository;
        this.salaryJobRepository = salaryJobRepository;
        this.typeJobRepository = typeJobRepository;
        this.javaMailSender = javaMailSender;
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
        jobTemp.setCreatedAt(new Date().toString());
        jobTemp.setUpdatedAt(new Date().toString());
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

        List<Job_Candidates> jobCandidatesList = job.getJobCandidates();
        jobCandidatesRepository.deleteAll(jobCandidatesList);

        List<Salary_Jobs> salaryJobsList = job.getSalaryJobs();
        salaryJobRepository.deleteAll(salaryJobsList);

        List<Type_Jobs> typeJobsList = job.getTypeJobs();
        typeJobRepository.deleteAll(typeJobsList);

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
        jobTemp.setUpdatedAt(new Date().toString());
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

    @Override
    public List<Job> searchJob(String name, String location, String leveljob, String salary) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);

        Root<Job> job = cq.from(Job.class);
        Join<Job, Company> company = job.join("companyId", JoinType.LEFT);
        Join<Job, Type_Jobs> types_jobs = job.join("typeJobs", JoinType.LEFT);
        Join<Type_Jobs, Field_Job> field_jobs = types_jobs.join("fieldJobs", JoinType.LEFT);
        Join<Job, Level_Job_Detail> level_job_detail = job.join("levelJobId", JoinType.LEFT);
        Join<Level_Job_Detail, Level_Job> level_job = level_job_detail.join("levelJobs", JoinType.LEFT);
        Join<Job, Address_Company> address_company = job.join("addressCompanyId", JoinType.LEFT);
        Join<Job, Salary_Jobs> salary_jobs = job.join("salaryJobs", JoinType.LEFT);
        Join<Salary_Jobs, Salary> salaryJoin = salary_jobs.join("salary_id", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(job.get("title"), "%" + name + "%"));

        if (location != null && !location.isEmpty()) {
            predicates.add(cb.like(address_company.get("address"), "%" + location + "%"));
        }

        if (salary != null && !salary.isEmpty()) {
            Long salaryId = Long.parseLong(salary);
            predicates.add(cb.equal(salaryJoin.get("id"), salaryId));
        }

        if (leveljob != null && !leveljob.isEmpty()) {
            Long levelJobId = Long.parseLong(leveljob);
            predicates.add(cb.equal(level_job.get("id"), levelJobId));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Job> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Job_Candidates applyJob(ApplyJobResponse applyJobResponse) {

        Job_Candidates jobCandidates = new Job_Candidates();

        Candidate candidate = candidateService.findCandidateById(applyJobResponse.getCandidate_id());
        Job job = jobRepository.findById(applyJobResponse.getJob_id()).orElse(null);

        if (candidate == null || job == null) {
            return null; // or throw an exception
        }

        jobCandidates.setCandidate_id(candidate);
        jobCandidates.setJob_id(job);
        jobCandidates.setStatus("1");
        jobCandidates.setContent(applyJobResponse.getContent());
        jobCandidates.setCv_url(applyJobResponse.getCv_url());

        jobCandidatesRepository.save(jobCandidates);

        return jobCandidates;
    }


    @Override
    public List<Job_Candidates> getJobAppliedCandidates(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job_Candidates> cq = cb.createQuery(Job_Candidates.class);

        Root<Job_Candidates> jobCandidates = cq.from(Job_Candidates.class);
        Join<Job_Candidates, Job> job = jobCandidates.join("job_id", JoinType.INNER);
        Join<Job_Candidates, Candidate> candidate = jobCandidates.join("candidate_id", JoinType.INNER);
        Join<Candidate, Account> account = candidate.join("account", JoinType.INNER);
        Join<Job, Company> company = job.join("companyId", JoinType.LEFT);
        Join<Job, Type_Jobs> types_jobs = job.join("typeJobs", JoinType.LEFT);
        Join<Type_Jobs, Field_Job> field_jobs = types_jobs.join("fieldJob", JoinType.LEFT);
        Join<Job, Level_Job_Detail> level_job_detail = job.join("levelJobId", JoinType.LEFT);
        Join<Level_Job_Detail, Level_Job> level_job = level_job_detail.join("levelJobs", JoinType.LEFT);
        Join<Job, Address_Company> address_company = job.join("addressCompanyId", JoinType.LEFT);
        Join<Job, Salary_Jobs> salary_jobs = job.join("salaryJobs", JoinType.LEFT);
        Join<Salary_Jobs, Salary> salaryJoin = salary_jobs.join("salary_id", JoinType.LEFT);

        Predicate usernamePredicate = cb.equal(account.get("userName"), username);
        cq.where(usernamePredicate);

        TypedQuery<Job_Candidates> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Job> getJobsForCompany(String userName, String status, String key) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);

        Root<Job> job = cq.from(Job.class);
        Join<Job, Company> company = job.join("companyId", JoinType.LEFT);
        Join<Company, Account> account = company.join("account", JoinType.LEFT);
        Join<Job, Address_Company> address_company = job.join("addressCompanyId", JoinType.LEFT);
        Join<Job, Type_Jobs> types_jobs = job.join("typeJobs", JoinType.LEFT);
        Join<Type_Jobs, Field_Job> field_jobs = types_jobs.join("fieldJob", JoinType.LEFT);
        Join<Job, Level_Job_Detail> level_job_detail = job.join("levelJobId", JoinType.LEFT);
        Join<Level_Job_Detail, Level_Job> level_job = level_job_detail.join("levelJobs", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(account.get("userName"), userName));

        if ("1".equals(status)) {
            predicates.add(cb.equal(job.get("status"), 1));
        } else if ("0".equals(status)) {
            predicates.add(cb.equal(job.get("status"), 0));
        }
//        } else if ("2".equals(status)) {
//            // No additional predicate needed
        if (key != null && !key.isEmpty()) {
            predicates.add(cb.like(job.get("title"), "%" + key + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(job.get("createdAt")));

        TypedQuery<Job> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void save(Job job) {
        jobRepository.save(job);
    }

    @Override
    public List<Job_Candidates> getJobAppliedCandidatesById(Long id, String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job_Candidates> cq = cb.createQuery(Job_Candidates.class);

        Root<Job_Candidates> jobCandidates = cq.from(Job_Candidates.class);
        Join<Job_Candidates, Job> job = jobCandidates.join("job_id", JoinType.INNER);
        Join<Job_Candidates, Candidate> candidate = jobCandidates.join("candidate_id", JoinType.INNER);
        Join<Candidate, Account> account = candidate.join("account", JoinType.INNER);
        Join<Job, Company> company = job.join("companyId", JoinType.LEFT);
        Join<Job, Type_Jobs> types_jobs = job.join("typeJobs", JoinType.LEFT);
        Join<Type_Jobs, Field_Job> field_jobs = types_jobs.join("fieldJob", JoinType.LEFT);
        Join<Job, Level_Job_Detail> level_job_detail = job.join("levelJobId", JoinType.LEFT);
        Join<Level_Job_Detail, Level_Job> level_job = level_job_detail.join("levelJobs", JoinType.LEFT);
        Join<Job, Address_Company> address_company = job.join("addressCompanyId", JoinType.LEFT);
        Join<Job, Salary_Jobs> salary_jobs = job.join("salaryJobs", JoinType.LEFT);
        Join<Salary_Jobs, Salary> salaryJoin = salary_jobs.join("salary_id", JoinType.LEFT);

        Predicate usernamePredicate = cb.equal(account.get("userName"), username);
        Predicate JobIdPredicate = cb.equal(job.get("id"), id);
        cq.where(usernamePredicate);
        cq.where(JobIdPredicate);

        TypedQuery<Job_Candidates> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Job_Candidates> getCandidatesbyIdJob(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job_Candidates> cq = cb.createQuery(Job_Candidates.class);

        Root<Job_Candidates> jobCandidates = cq.from(Job_Candidates.class);
        Join<Job_Candidates, Job> job = jobCandidates.join("job_id", JoinType.INNER);
        Join<Job_Candidates, Candidate> candidate = jobCandidates.join("candidate_id", JoinType.INNER);

        Predicate JobIdPredicate = cb.equal(job.get("id"), id);
        cq.where(JobIdPredicate);

        TypedQuery<Job_Candidates> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Transactional
    public void updateInterview(Long id, String interviewDay, String interviewAddress, String nameCompany, String emailCompany) {
        // Fetch the Job_Candidates object with the given id
        Job_Candidates jobCandidates = jobCandidatesRepository.findById(id).orElse(null);
        if (jobCandidates == null) {
            throw new RuntimeException("Job_Candidates not found");
        }

        // Fetch the Candidate and Account objects
        Candidate candidate = jobCandidates.getCandidate_id();
        Account account = candidate.getAccount();

        // Update the interview_day and status
        jobCandidates.setInterview_day(interviewDay);
        jobCandidates.setStatus("2");
        jobCandidatesRepository.save(jobCandidates);

        // Email the candidate
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(sender);
        mail.setTo(account.getEmail());
        mail.setSubject("Thư mời phỏng vấn");
        mail.setText("Xin chào " + candidate.getName() + ", Chúng tôi đánh giá cao CV của bạn. Nên chúng tôi muốn hẹn bạn 1 buổi phỏng vấn :\n" +
                "Ngày phỏng vấn: " + interviewDay + "\n" +
                "Địa chỉ phỏng vấn: " + interviewAddress + "\n" +
                "Tên công ty: " + nameCompany + "\n" +
                "Email công ty: " + emailCompany + "\n" +
                "Chúng tôi rất mong sớm nhận được phản hồi từ bạn. Xin cảm ơn!");

        javaMailSender.send(mail);
    }

    @Transactional
    @Override
    public void cancelCandidate(Long idApply, String nameCompany) {

        Job_Candidates jobCandidates = jobCandidatesRepository.findById(idApply).orElse(null);
        if (jobCandidates == null) {
            throw new RuntimeException("Job_Candidates not found");
        }

        // Fetch the Candidate and Account objects
        Candidate candidate = jobCandidates.getCandidate_id();
        Account account = candidate.getAccount();

        jobCandidates.setStatus("0");
        jobCandidatesRepository.save(jobCandidates);

        // Email the candidate
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(sender);
        mail.setTo(account.getEmail());
        mail.setSubject("THƯ CẢM ƠN & THÔNG BÁO KẾT QUẢ CV");
        mail.setText("Xin chào " + candidate.getName() + ", Chúng tôi đánh giá cao CV của bạn. Nhưng rất tiếc, bạn hiện tại chưa phù hợp với công ty chúng tôi :\n" +
                "Tên công ty: " + nameCompany + "\n" +
                "Chúng tôi rất mong sẽ được hợp tác với bạn trong thời gian tới. Xin cảm ơn!");

        javaMailSender.send(mail);
    }

}
