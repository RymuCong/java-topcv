package com.t3h.topcv.repository.Candidate;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.candidate.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByCandidateId(Candidate candidateById);
}
