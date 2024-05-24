package com.t3h.topcv.repository.Candidate;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.candidate.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByCandidateId(Candidate candidateById);
}
