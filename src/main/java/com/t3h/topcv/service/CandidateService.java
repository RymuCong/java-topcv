package com.t3h.topcv.service;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CandidateService {

    Candidate findCandidateById(Long id);

    List <Candidate> findAllCandidates();

    Candidate createCandidate(Candidate candidate, Long accountId);

    Candidate getCandidateByAccountId(Long id);

    Candidate findCandidateByAccount(Long accountId);

    List<Candidate> findAllByStatus(Integer status);


    List<Candidate> findAllByPosition(String position);

    void save(Candidate candidate);

    void delete(Long id);
}
