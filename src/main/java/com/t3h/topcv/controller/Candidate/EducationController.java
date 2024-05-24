package com.t3h.topcv.controller.Candidate;

import com.t3h.topcv.entity.candidate.Education;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.Candidate.EducationRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EducationController {

    private final EducationRepository educationRepo;

    private final CandidateService candidateService;

    @Autowired
    public EducationController(EducationRepository educationRepo, CandidateService candidateService) {
        this.educationRepo = educationRepo;
        this.candidateService = candidateService;
    }

    @GetMapping("/educations")
    public List<Education> getAllEducations() {
        return educationRepo.findAll();
    }

    @GetMapping("/educations/{id}")
    public Education getEducationById(@PathVariable Long id) {
        return educationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with id " + id));
    }

    @GetMapping("/educations/candidate/{candidateId}")
    public List<Education> getEducationsByCandidateId(@PathVariable Long candidateId) {
        return educationRepo.findByCandidateId(candidateService.findCandidateById(candidateId));
    }

    @PostMapping("/educations")
    public Education createEducation(@RequestBody Education education) {
        return educationRepo.save(education);
    }

    @PutMapping("/educations/{id}")
    public Education updateEducation(@PathVariable Long id, @RequestBody Education education) {
        Education educationTemp = educationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with id " + id));

        educationTemp.setMajor(education.getMajor());
        educationTemp.setDescription(education.getDescription());
        educationTemp.setStartAt(education.getStartAt());
        educationTemp.setEndAt(education.getEndAt());
        educationTemp.setSchool(education.getSchool());
        educationTemp.setStatus(education.getStatus());
        educationTemp.setCandidateId(education.getCandidateId());
        return educationRepo.save(educationTemp);
    }

    @DeleteMapping("/educations/{id}")
    public void deleteEducation(@PathVariable Long id) {
        educationRepo.deleteById(id);
    }
}
