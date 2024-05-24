package com.t3h.topcv.repository.Candidate;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>{
    Candidate findByAccount(Account account);

    List<Candidate> findAllByStatus(Integer status);

    List<Candidate> findAllByPosition(String position);
}
