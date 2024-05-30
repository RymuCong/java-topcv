package com.t3h.topcv.service.Candidate;

import com.t3h.topcv.entity.candidate.*;
import com.t3h.topcv.entity.job.Job_Candidates;

import java.util.List;

public interface CandidateService {

    Candidate findCandidateById(Long id);

    List <Candidate> findAllCandidates();

    Candidate createCandidate(Candidate candidate, Long accountId);

    Candidate getCandidateByAccountId(Long id);

    Candidate findCandidateByAccount(Long accountId);

    List<Candidate> findAllByStatus(Integer status);


    List<Candidate> findAllByPosition(String position);

    void save(Candidate candidate);

    void delete(Long id);

    List<Candidate> getHomepageCandidate();

    List<Job_Candidates> getJobSaveCandidate(String username);

    Candidate updateAboutMe (String aboutMe, String username);

    void updateCandidate(Candidate candidate, String username);

    void createEducation(Education education, String username);

    void createExperience(Experience experience, String username);

    void createProject(Project project, String username);

    void createSkill(Skill skill, String username);

    void createCertificate(Certificate certificate, String username);

    void updateEducation(Education education, String username);

    void updateExperience(Experience experience, String username);

    void updateProject(Project project, String username);

    void updateSkill(Skill skill, String username);

    void updateCertificate(Certificate certificate, String username);

    void deleteEducation(Long id, String username);

    void deleteExperience(Long id, String username);

    void deleteProject(Long id, String username);

    void deleteSkill(Long id, String username);

    void deleteCertificate(Long id, String username);
}
