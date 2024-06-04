package com.t3h.topcv.controller.Candidate;

import com.t3h.topcv.dto.*;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.*;
import com.t3h.topcv.entity.job.Job_Candidates;
import com.t3h.topcv.entity.job.Level_Job;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.repository.Job.LevelJobRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class CandidateController {

    private final CandidateService candidateService;

    private final AccountRepository accountRepo;

    private final LevelJobRepository levelJobRepo;

    @Autowired
    public CandidateController(CandidateService candidateService, AccountRepository accountRepository, LevelJobRepository levelJobRepo) {
        this.candidateService = candidateService;
        this.accountRepo = accountRepository;
        this.levelJobRepo = levelJobRepo;
    }

    @GetMapping("/candidates/getAll")
    public ResponseEntity<?> getAllCandidate() {

        List <Candidate> candidates = candidateService.findAllCandidates();

        CandidateResponse response = new CandidateResponse();

        response.setMessage("success");
        response.setData(candidates);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get top 6 candidates
    @GetMapping("/candidates/firstPage")
    public ResponseEntity<?> getHomepageCandidate() {
        List<Candidate> candidates = candidateService.getHomepageCandidate();

        CandidateResponse response = new CandidateResponse();

        response.setMessage("success");
        response.setData(candidates);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("candidates/getJobSave")
    public ResponseEntity<?> getJobSaveCandidate(Principal principal) {
        try {
            String username = principal.getName();
            List<Job_Candidates> result = candidateService.getJobSaveCandidate(username);
            JobCandidateResponse response = new JobCandidateResponse();
            response.setMessage("success");
            response.setData(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("candidates/updateAboutMe")
    public ResponseEntity<?> updateAboutMe(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String aboutMe = body.get("aboutMe");
            String email = userDetails.getUsername();
            Candidate result = candidateService.updateAboutMe(aboutMe, email);
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/candidates/updateInfoCandidate")
    public ResponseEntity<?> updateInfoCandidate(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String userName = userDetails.getUsername();
            Candidate candidate = candidateService.findCandidateByAccount(accountRepo.findByUserName(userName).getId());
            candidate.setName(body.get("name"));
            candidate.setPhone(body.get("phone"));
            candidate.setAddress(body.get("address"));
            candidate.setBirthday(body.get("birthday"));
            candidate.setGender(body.get("gender"));
            candidate.setPosition(body.get("position"));
            candidate.setLinkGithub(body.get("link_git"));
            candidate.setUpdatedAt(new Date());
            candidate.setAvatar(body.get("avatar"));

            candidateService.updateCandidate(candidate,userName);
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping("candidate/createEducation")
    public ResponseEntity<?> createEducation(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Education education = new Education();
            education.setSchool(body.get("school"));
            education.setMajor(body.get("major"));
            education.setStartAt(body.get("startAt"));
            education.setEndAt(body.get("endAt"));
            education.setDescription(body.get("info"));
            education.setStatus(1);

            candidateService.createEducation(education, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("candidate/updateEducation/{id}")
    public ResponseEntity<?> updateEducation(@PathVariable Long id, @RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Education education = new Education();
            education.setId(id);
            education.setSchool(body.get("school"));
            education.setMajor(body.get("major"));
            education.setStartAt(body.get("startAt"));
            education.setEndAt(body.get("endAt"));
            education.setDescription(body.get("info"));
            education.setStatus(1);

            candidateService.updateEducation(education, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật này");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("candidate/createExperience")
    public ResponseEntity<?> createExperience(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Experience experience = new Experience();
            experience.setCompanyName(body.get("company"));
            experience.setPosition(body.get("position"));
            experience.setStartDate(body.get("startAt"));
            experience.setEndDate(body.get("endAt"));
            experience.setDescription(body.get("info"));
            experience.setStatus(1);

            candidateService.createExperience(experience, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("candidate/updateExperience/{id}")
    public ResponseEntity<?> updateExperience(@PathVariable Long id, @RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Experience experience = new Experience();
            experience.setId(id);
            experience.setCompanyName(body.get("company"));
            experience.setPosition(body.get("position"));
            experience.setStartDate(body.get("startAt"));
            experience.setEndDate(body.get("endAt"));
            experience.setDescription(body.get("info"));
            experience.setStatus(1);

            candidateService.updateExperience(experience, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("candidate/updateProject/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Project project = new Project();
            project.setId(id);
            project.setName(body.get("name"));
            project.setStartAt(body.get("startAt"));
            project.setEndAt(body.get("endAt"));
            project.setDescription(body.get("info"));
            project.setLink(body.get("link"));
            project.setStatus(1);

            candidateService.updateProject(project, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("candidate/updateSkill/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Long id, @RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Skill skill = new Skill();
            skill.setId(id);
            skill.setName(body.get("name"));

            // Retrieve Level_Job object from its repository
            Level_Job levelJob = levelJobRepo.findById(Long.parseLong(body.get("level")))
                    .orElseThrow(() -> new RuntimeException("Level job not found with id " + body.get("level")));
            skill.setLevelJobs(levelJob);

            // Retrieve Candidate object from its repository
            Candidate candidate = candidateService.findCandidateByAccount(Long.parseLong(body.get("candidateId")));
            skill.setCandidateId(candidate);

            candidateService.updateSkill(skill, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật mới");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("candidate/updateCertificate/{id}")
    public ResponseEntity<?> updateCertificate(@PathVariable Long id, @RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Certificate certificate = new Certificate();
            certificate.setId(id);
            certificate.setName(body.get("name"));
            certificate.setStartAt(body.get("startAt"));
            certificate.setEndAt(body.get("endAt"));
            certificate.setOrganization(body.get("organization"));
            certificate.setDescription(body.get("info"));
            certificate.setStatus(1);

            candidateService.updateCertificate(certificate, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/candidates/getInfo")
    public ResponseEntity<?> getInfoCandidate(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(accountRepo.findByUserName(userDetails.getUsername()).getId());
        Candidate candidate = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId());
//        System.out.println(candidate.getName());
        SingleResponse response = new SingleResponse();
        response.setMessage("success");
        response.setData(candidate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/candidates/getInforCandidateById/{id}")
    public ResponseEntity<?> getCandidateById(@PathVariable Long id) {

        Candidate candidate = candidateService.findCandidateById(id);

        SingleResponse response = new SingleResponse();
        response.setMessage("success");
        response.setData(candidate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/candidates/account/{accountId}")
    public ResponseEntity<?> getCandidatesByAccountId(@PathVariable Long accountId) {
        Candidate candidate = candidateService.findCandidateByAccount(accountId);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @GetMapping("/candidates/status/{status}")
    public ResponseEntity<?> getCandidatesByStatus(@PathVariable Integer status) {
        List<Candidate> candidates = candidateService.findAllByStatus(status);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/candidates/position/{position}")
    public ResponseEntity<?> getCandidatesByPosition(@PathVariable String position) {
        List<Candidate> candidates = candidateService.findAllByPosition(position);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @PostMapping("/candidates/{accountId}")
    public ResponseEntity<?> createCandidate(@PathVariable Long accountId, @RequestBody Candidate candidate) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));

        Candidate candidateTemp = candidateService.createCandidate(candidate, accountId);
        candidateService.save(candidateTemp);

        return new ResponseEntity<>(candidateTemp, HttpStatus.OK);
    }

    @PutMapping("/candidates/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        Candidate candidateUpdate = candidateService.findCandidateById(id);

        if (candidateUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        candidateUpdate.setName(candidate.getName());
        candidateUpdate.setEmail(candidate.getEmail());
        candidateUpdate.setPhone(candidate.getPhone());
        candidateUpdate.setAddress(candidate.getAddress());
        candidateUpdate.setBirthday(candidate.getBirthday());
        candidateUpdate.setGender(candidate.getGender());
        candidateUpdate.setStatus(candidate.getStatus());
        candidateUpdate.setPosition(candidate.getPosition());
        candidateUpdate.setLinkLinkedin(candidate.getLinkLinkedin());
        candidateUpdate.setLinkGithub(candidate.getLinkGithub());
        candidateUpdate.setUpdatedAt(candidate.getUpdatedAt());
        candidateUpdate.setAvatar(candidate.getAvatar());
        candidateUpdate.setDescription(candidate.getDescription());
        candidateUpdate.setCertificates(candidate.getCertificates());
        candidateUpdate.setEducations(candidate.getEducations());
        candidateUpdate.setExperiences(candidate.getExperiences());
        candidateUpdate.setJobCandidates(candidate.getJobCandidates());

        candidateService.save(candidateUpdate);

        return new ResponseEntity<>(candidateUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {

            Candidate candidate = candidateService.findCandidateById(id);

            if (candidate == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            candidateService.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/candidate/createProject")
    public ResponseEntity<?> createProject(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Project project = new Project();
            project.setName(body.get("name"));
            project.setStartAt(body.get("start_at"));
            project.setEndAt(body.get("end_at"));
            project.setDescription(body.get("info"));
            project.setLink(body.get("link"));
            project.setStatus(1);

            candidateService.createProject(project, userDetails.getUsername());

            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/candidate/createSkill")
    public ResponseEntity<?> createSkill(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Skill skill = new Skill();
            skill.setName(body.get("name"));

            Level_Job levelJob = levelJobRepo.findById(Long.parseLong(body.get("leveljob_id")))
                    .orElseThrow(() -> new RuntimeException("Level job not found with id " + body.get("leveljob_id")));
            skill.setLevelJobs(levelJob);

            candidateService.createSkill(skill, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/candidate/createCertificate")
    public ResponseEntity<?> createCertificate(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Certificate certificate = new Certificate();
            certificate.setName(body.get("name"));
            certificate.setStartAt(body.get("start_at"));
            certificate.setEndAt(body.get("end_at"));
            certificate.setOrganization(body.get("organization"));
            certificate.setDescription(body.get("info"));
            certificate.setStatus(1);

            candidateService.createCertificate(certificate, userDetails.getUsername());
            return ResponseEntity.ok().body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/candidate/getAllEducationCandidate")
    public ResponseEntity<?> getAllEducationCandidate(@AuthenticationPrincipal UserDetails userDetails) {
        List <Education> educations = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId()).getEducations();

        EducationResponse response = new EducationResponse();
        response.setMessage("success");
        response.setData(educations);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/candidate/getAllExperience")
    public ResponseEntity<?> getAllExperience(@AuthenticationPrincipal UserDetails userDetails) {
        List <Experience> experiences = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId()).getExperiences();

        ExperienceResponse response = new ExperienceResponse();
        response.setMessage("success");
        response.setData(experiences);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/candidate/getAllSkills")
    public ResponseEntity<?> getAllSkill(@AuthenticationPrincipal UserDetails userDetails) {
        List <Skill> skills = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId()).getSkills();

        SkillResponse response = new SkillResponse();
        response.setMessage("success");
        response.setData(skills);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/candidate/getAllProject")
    public ResponseEntity<?> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        List <Project> projects = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId()).getProjects();

        ProjectResponse response = new ProjectResponse();
        response.setMessage("success");
        response.setData(projects);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/candidate/getAllCertificate")
    public ResponseEntity<?> getAllCertificates(@AuthenticationPrincipal UserDetails userDetails) {
        List <Certificate> certificates = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId()).getCertificates();

        CertificateResponse response = new CertificateResponse();
        response.setMessage("success");
        response.setData(certificates);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/candidate/deleteEducation/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        candidateService.deleteEducation(id, userDetails.getUsername());
        return ResponseEntity.ok().body("Xóa thành công dữ liệu!");
    }

    @DeleteMapping("/candidate/deleteExperience/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        candidateService.deleteExperience(id, userDetails.getUsername());
        return ResponseEntity.ok().body("Xóa thành công dữ liệu!");
    }

    @DeleteMapping("/candidate/deleteProject/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        candidateService.deleteProject(id, userDetails.getUsername());
        return ResponseEntity.ok().body("Xóa thành công dữ liệu!");
    }

    @DeleteMapping("/candidate/deleteSkill/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        candidateService.deleteSkill(id, userDetails.getUsername());
        return ResponseEntity.ok().body("Xóa thành công dữ liệu!");
    }

    @GetMapping("/candidates/getAllInformation")
    public ResponseEntity<?> getAllInformation(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Candidate candidate = candidateService.findCandidateByAccount(accountRepo.findByUserName(userDetails.getUsername()).getId());
            SingleResponse response = new SingleResponse();
            response.setMessage("success");
            response.setData(candidate);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
