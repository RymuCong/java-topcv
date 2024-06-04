package com.t3h.topcv.controller.Candidate;

import com.t3h.topcv.entity.candidate.Skill;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.Candidate.SkillRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SkillController {

    private final SkillRepository skillRepo;

    private final CandidateService candidateService;

    @Autowired
    public SkillController(SkillRepository skillRepo, CandidateService candidateService) {
        this.skillRepo = skillRepo;
        this.candidateService = candidateService;
    }

    @GetMapping("/skills")
    public List<Skill> getAllSkills() {
        return skillRepo.findAll();
    }

    @GetMapping("/skills/{id}")
    public Skill getSkillById(@PathVariable Long id) {
        return skillRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id " + id));
    }

    @GetMapping("/skills/candidate/{candidateId}")
    public List<Skill> getSkillsByCandidateId(@PathVariable Long candidateId) {
        return skillRepo.findByCandidateId(candidateService.findCandidateById(candidateId));
    }

    @PostMapping("/skills")
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepo.save(skill);
    }

    @PutMapping("/skills/{id}")
    public Skill updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Skill skillTemp = skillRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id " + id));
        skillTemp.setName(skill.getName());
        skillTemp.setLevelJobs(skill.getLevelJobs());
        skillTemp.setLevelJobs(skill.getLevelJobs());
        skillTemp.setCandidateId(skill.getCandidateId());
        return skillRepo.save(skillTemp);
    }

    @DeleteMapping("/skills/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillRepo.deleteById(id);
    }
}
