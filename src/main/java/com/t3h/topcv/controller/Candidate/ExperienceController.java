package com.t3h.topcv.controller.Candidate;

import com.t3h.topcv.entity.candidate.Experience;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.Candidate.ExperienceRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExperienceController {

    private final ExperienceRepository experienceRepo;

    private final CandidateService candidateService;

    @Autowired
    public ExperienceController(ExperienceRepository experienceRepo, CandidateService candidateService) {
        this.experienceRepo = experienceRepo;
        this.candidateService = candidateService;
    }

    @GetMapping("/experiences")
    public List<Experience> getAllExperiences() {
        return experienceRepo.findAll();
    }

    @GetMapping("/experiences/{id}")
    public Experience getExperienceById(@PathVariable Long id) {
        return experienceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id " + id));
    }

    @GetMapping("/experiences/candidate/{candidateId}")
    public List<Experience> getExperiencesByCandidateId(@PathVariable Long candidateId) {
        return experienceRepo.findByCandidateId(candidateService.findCandidateById(candidateId));
    }

    @PostMapping("/experiences")
    public Experience createExperience(@RequestBody Experience experience) {
        return experienceRepo.save(experience);
    }

    @PutMapping("/experiences/{id}")
    public Experience updateExperience(@PathVariable Long id, @RequestBody Experience experience) {
        Experience experienceTemp = experienceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id " + id));
        experienceTemp.setCompanyName(experience.getCompanyName());
        experienceTemp.setDescription(experience.getDescription());
        experienceTemp.setStartDate(experience.getStartDate());
        experienceTemp.setEndDate(experience.getEndDate());
        experienceTemp.setPosition(experience.getPosition());
        experienceTemp.setStatus(experience.getStatus());
        experienceTemp.setCandidateId(experience.getCandidateId());
        return experienceRepo.save(experienceTemp);
    }

    @DeleteMapping("/experiences/{id}")
    public void deleteExperience(@PathVariable Long id) {
        experienceRepo.deleteById(id);
    }
}
