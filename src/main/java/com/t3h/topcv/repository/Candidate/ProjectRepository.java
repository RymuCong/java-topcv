package com.t3h.topcv.repository.Candidate;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.candidate.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCandidateId(Candidate candidateById);
}
