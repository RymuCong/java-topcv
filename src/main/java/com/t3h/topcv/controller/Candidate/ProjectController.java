package com.t3h.topcv.controller.Candidate;

import com.t3h.topcv.entity.candidate.Project;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.Candidate.ProjectRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepo;

    private final CandidateService candidateService;

    @Autowired
    public ProjectController(ProjectRepository projectRepo, CandidateService candidateService) {
        this.projectRepo = projectRepo;
        this.candidateService = candidateService;
    }

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @GetMapping("/projects/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
    }

    @GetMapping("/projects/candidate/{candidateId}")
    public List<Project> getProjectsByCandidateId(@PathVariable Long candidateId) {
        return projectRepo.findByCandidateId(candidateService.findCandidateById(candidateId));
    }

    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) {
        return projectRepo.save(project);
    }

    @PutMapping("/projects/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project projectTemp = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        projectTemp.setName(project.getName());
        projectTemp.setDescription(project.getDescription());
        projectTemp.setStartAt(project.getStartAt());
        projectTemp.setEndAt(project.getEndAt());
        projectTemp.setLink(project.getLink());
        projectTemp.setStatus(project.getStatus());
        projectTemp.setCandidateId(project.getCandidateId());
        return projectRepo.save(projectTemp);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepo.deleteById(id);
    }
}
