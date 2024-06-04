package com.t3h.topcv.repository.Job;

import com.t3h.topcv.entity.job.Job_Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCandidateRepository extends JpaRepository<Job_Candidates, Long> {

//    List<Job_Candidates> findByCandidate_id(Long candidate_id);

}
