package com.t3h.topcv.repository.Candidate;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.candidate.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCandidateId(Candidate candidateById);
}
