package com.t3h.topcv.repository.Candidate;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.candidate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByCandidateId(Candidate candidateId);
}
