package com.t3h.topcv.service;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService{
    private final CandidateRepository candidateRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, AccountRepository accountRepository) {
        this.candidateRepository = candidateRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));
    }

    @Override
    public List<Candidate> findAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate createCandidate(Candidate candidate, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));

        Candidate candidateTemp = new Candidate();
        candidateTemp.setName(candidate.getName());
        candidateTemp.setEmail(candidate.getEmail());
        candidateTemp.setPhone(candidate.getPhone());
        candidateTemp.setAddress(candidate.getAddress());
        candidateTemp.setBirthday(candidate.getBirthday());
        candidateTemp.setGender(candidate.getGender());
        candidateTemp.setStatus(candidate.getStatus());
        candidateTemp.setPosition(candidate.getPosition());
        candidateTemp.setLinkLinkedin(candidate.getLinkLinkedin());
        candidateTemp.setLinkGithub(candidate.getLinkGithub());
        candidateTemp.setCreatedAt(candidate.getCreatedAt());
        candidateTemp.setUpdatedAt(candidate.getUpdatedAt());
        candidateTemp.setAvatar(candidate.getAvatar());
        candidateTemp.setDescription(candidate.getDescription());
        candidateTemp.setCertificates(candidate.getCertificates());
        candidateTemp.setEducations(candidate.getEducations());
        candidateTemp.setExperiences(candidate.getExperiences());
        candidateTemp.setJobCandidates(candidate.getJobCandidates());
        candidateTemp.setAccount(account);

        return candidateTemp;
    }

    @Override
    public Candidate getCandidateByAccountId(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
        Candidate candidate = candidateRepository.findByAccount(account);

        if (candidate == null) {
            throw new ResourceNotFoundException("Candidate not found with account id " + id);
        }

        return candidate;
    }

    @Override
    public Candidate findCandidateByAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));
        return candidateRepository.findByAccount(account);
    }

    @Override
    public List<Candidate> findAllByStatus(Integer status) {
        return candidateRepository.findAllByStatus(status);
    }

    @Override
    public List<Candidate> findAllByPosition(String position) {
        return candidateRepository.findAllByPosition(position);
    }

    @Override
    @Transactional
    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));

        candidate.setAccount(null);

        candidateRepository.delete(candidate);
    }
}
