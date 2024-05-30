package com.t3h.topcv.service.Candidate;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.*;
import com.t3h.topcv.entity.job.Job_Candidates;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.repository.Candidate.CandidateRepository;
import com.t3h.topcv.repository.Job.JobCandidateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

    private final AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, AccountRepository accountRepository, JobCandidateRepository jobCandidateRepository) {
        this.candidateRepository = candidateRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));
    }

    @Override
    public List<Candidate> findAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate createCandidate(Candidate candidate, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));

        Candidate candidateTemp = new Candidate();
        candidateTemp.setName(candidate.getName());
        candidateTemp.setEmail(candidate.getEmail());
        candidateTemp.setPhone(candidate.getPhone());
        candidateTemp.setAddress(candidate.getAddress());
        candidateTemp.setBirthday(candidate.getBirthday());
        candidateTemp.setGender(candidate.getGender());
        candidateTemp.setStatus(candidate.getStatus());
        candidateTemp.setPosition(candidate.getPosition());
        candidateTemp.setLinkLinkedin(candidate.getLinkLinkedin());
        candidateTemp.setLinkGithub(candidate.getLinkGithub());
        candidateTemp.setCreatedAt(candidate.getCreatedAt());
        candidateTemp.setUpdatedAt(candidate.getUpdatedAt());
        candidateTemp.setAvatar(candidate.getAvatar());
        candidateTemp.setDescription(candidate.getDescription());
        candidateTemp.setCertificates(candidate.getCertificates());
        candidateTemp.setEducations(candidate.getEducations());
        candidateTemp.setExperiences(candidate.getExperiences());
        candidateTemp.setJobCandidates(candidate.getJobCandidates());
        candidateTemp.setAccount(account);

        return candidateTemp;
    }

    @Override
    public Candidate getCandidateByAccountId(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
        Candidate candidate = candidateRepository.findByAccount(account);

        if (candidate == null) {
            throw new ResourceNotFoundException("Candidate not found with account id " + id);
        }

        return candidate;
    }

    @Override
    public Candidate findCandidateByAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));
        return candidateRepository.findByAccount(account);
    }

    @Override
    public List<Candidate> findAllByStatus(Integer status) {
        return candidateRepository.findAllByStatus(status);
    }

    @Override
    public List<Candidate> findAllByPosition(String position) {
        return candidateRepository.findAllByPosition(position);
    }

    @Override
    @Transactional
    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));

        candidate.getAccount().setCandidate(null);

        candidateRepository.delete(candidate);
    }

    @Override
    public List<Candidate> getHomepageCandidate() {
        // top 6 candidate
        return candidateRepository.findTop6ByOrderByCreatedAtDesc();
    }

    @Override
    public List<Job_Candidates> getJobSaveCandidate(String username) {
        String queryStr = "SELECT j FROM Job_Candidates j " +
                "JOIN FETCH j.job_id " +
                "JOIN FETCH j.candidate_id c " +
                "JOIN FETCH c.account a " +
                "WHERE a.userName = :username";

        TypedQuery<Job_Candidates> query = entityManager.createQuery(queryStr, Job_Candidates.class);
        query.setParameter("username", username);

        return query.getResultList();
    }

    public Candidate CheckUserName(String username) {
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            throw new RuntimeException("Account not found with username " + username);
        }
        Candidate candidate = candidateRepository.findByAccount(account);
        if (candidate == null) {
            throw new RuntimeException("Candidate not found for account with username " + username);
        }
        return candidate;
    }

    @Transactional
    @Override
    public Candidate updateAboutMe(String aboutMe, String username) {
        Candidate candidate = CheckUserName(username);
        candidate.setAboutMe(aboutMe);
        return candidateRepository.save(candidate);
    }

    @Override
    public void updateCandidate(Candidate candidate, String username) {
        Candidate candidateTemp = CheckUserName(username);
        candidateTemp.setName(candidate.getName());
        candidateTemp.setEmail(candidate.getEmail());
        candidateTemp.setPhone(candidate.getPhone());
        candidateTemp.setAddress(candidate.getAddress());
        candidateTemp.setBirthday(candidate.getBirthday());
        candidateTemp.setGender(candidate.getGender());
        candidateTemp.setStatus(candidate.getStatus());
        candidateTemp.setPosition(candidate.getPosition());
        candidateTemp.setLinkLinkedin(candidate.getLinkLinkedin());
        candidateTemp.setLinkGithub(candidate.getLinkGithub());
        candidateTemp.setAvatar(candidate.getAvatar());
        candidateTemp.setDescription(candidate.getDescription());

        candidateRepository.save(candidateTemp);
    }

    @Override
    public void createEducation(Education education, String username) {
        Candidate candidate = CheckUserName(username);
        education.setCandidateId(candidate);
        candidate.getEducations().add(education);
        candidateRepository.save(candidate);
    }

    @Override
    public void createExperience(Experience experience, String username) {
        Candidate candidate = CheckUserName(username);
        experience.setCandidateId(candidate);
        candidate.getExperiences().add(experience);
        candidateRepository.save(candidate);
    }

    @Override
    public void createProject(Project project, String username) {
        Candidate candidate = CheckUserName(username);
        project.setCandidateId(candidate);
        candidate.getProjects().add(project);
        candidateRepository.save(candidate);
    }

    @Override
    public void createSkill(Skill skill, String username) {
        Candidate candidate = CheckUserName(username);
        skill.setCandidateId(candidate);
        candidate.getSkills().add(skill);
        candidateRepository.save(candidate);
    }

    @Override
    public void createCertificate(Certificate certificate, String username) {
        Candidate candidate = CheckUserName(username);
        certificate.setCandidateId(candidate);
        candidate.getCertificates().add(certificate);
        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void updateEducation(Education education, String username) {
        Candidate candidate = CheckUserName(username);
        Education educationTemp = candidate.getEducations().stream()
                .filter(e -> e.getId().equals(education.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Education not found with id " + education.getId()));

        educationTemp.setMajor(education.getMajor());
        educationTemp.setSchool(education.getSchool());
        educationTemp.setStartAt(education.getStartAt());
        educationTemp.setEndAt(education.getEndAt());
        educationTemp.setStatus(education.getStatus());
        educationTemp.setDescription(education.getDescription());
        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void updateExperience(Experience experience, String username) {
        Candidate candidate = CheckUserName(username);
        Experience experienceTemp = candidate.getExperiences().stream()
                .filter(e -> e.getId().equals(experience.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Experience not found with id " + experience.getId()));

        experienceTemp.setCompanyName(experience.getCompanyName());
        experienceTemp.setPosition(experience.getPosition());
        experienceTemp.setStartDate(experience.getStartDate());
        experienceTemp.setEndDate(experience.getEndDate());
        experienceTemp.setStatus(experience.getStatus());
        experienceTemp.setDescription(experience.getDescription());
        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void updateProject(Project project, String username) {
        Candidate candidate = CheckUserName(username);
        Project projectTemp = candidate.getProjects().stream()
                .filter(e -> e.getId().equals(project.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found with id " + project.getId()));

        projectTemp.setName(project.getName());
        projectTemp.setLink(project.getLink());
        projectTemp.setStartAt(project.getStartAt());
        projectTemp.setEndAt(project.getEndAt());
        projectTemp.setStatus(project.getStatus());
        projectTemp.setDescription(project.getDescription());

        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void updateSkill(Skill skill, String username) {
        Candidate candidate = CheckUserName(username);
        Skill skillTemp = candidate.getSkills().stream()
                .filter(e -> e.getId().equals(skill.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Skill not found with id " + skill.getId()));

        skillTemp.setName(skill.getName());
        skillTemp.setLevelJobs(skill.getLevelJobs());
        skillTemp.setCandidateId(skill.getCandidateId());

        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void updateCertificate(Certificate certificate, String username) {
        Candidate candidate = CheckUserName(username);
        Certificate certificateTemp = candidate.getCertificates().stream()
                .filter(e -> e.getId().equals(certificate.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Certificate not found with id " + certificate.getId()));

        certificateTemp.setName(certificate.getName());
        certificateTemp.setOrganization(certificate.getOrganization());
        certificateTemp.setStartAt(certificate.getStartAt());
        certificateTemp.setEndAt(certificate.getEndAt());
        certificateTemp.setStatus(certificate.getStatus());
        certificateTemp.setDescription(certificate.getDescription());

        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void deleteEducation(Long id, String username) {
        Candidate candidate = CheckUserName(username);
        Education education = candidate.getEducations().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Education not found with id " + id));
        candidate.getEducations().remove(education);
        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void deleteExperience(Long id, String username) {
        Candidate candidate = CheckUserName(username);
        Experience experience = candidate.getExperiences().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Experience not found with id " + id));
        candidate.getExperiences().remove(experience);
        candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void deleteProject(Long id, String username) {
        Candidate candidate = CheckUserName(username);
        Project project = candidate.getProjects().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found with id " + id));
        candidate.getProjects().remove(project);
        candidateRepository.save(candidate);
    }

    @Override
    public void deleteSkill(Long id, String username) {
        Candidate candidate = CheckUserName(username);
        Skill skill = candidate.getSkills().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Skill not found with id " + id));
        candidate.getSkills().remove(skill);
        candidateRepository.save(candidate);
    }

    @Override
    public void deleteCertificate(Long id, String username) {
        Candidate candidate = CheckUserName(username);
        Certificate certificate = candidate.getCertificates().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Certificate not found with id " + id));
        candidate.getCertificates().remove(certificate);
        candidateRepository.save(candidate);
    }

}
