package com.t3h.topcv.service.Candidate;

import com.t3h.topcv.entity.candidate.Candidate;

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
