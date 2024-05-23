package com.t3h.topcv.controller;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CandidateController {

    private final CandidateService candidateService;

    private final AccountRepository accountRepo;

    @Autowired
    public CandidateController(CandidateService candidateService, AccountRepository accountRepository) {
        this.candidateService = candidateService;
        this.accountRepo = accountRepository;
    }

    @GetMapping("/candidates")
    public ResponseEntity<?> getAllCandidate() {

        List <Candidate> candidates = candidateService.findAllCandidates();

        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/candidates/{id}")
    public ResponseEntity<?> getCandidateById(@PathVariable Long id) {

        Candidate candidate = candidateService.findCandidateById(id);

        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @GetMapping("/candidates/account/{accountId}")
    public ResponseEntity<?> getCandidatesByAccountId(@PathVariable Long accountId) {
        Candidate candidate = candidateService.findCandidateByAccount(accountId);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @GetMapping("/candidates/status/{status}")
    public ResponseEntity<?> getCandidatesByStatus(@PathVariable Integer status) {
        List<Candidate> candidates = candidateService.findAllByStatus(status);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/candidates/position/{position}")
    public ResponseEntity<?> getCandidatesByPosition(@PathVariable String position) {
        List<Candidate> candidates = candidateService.findAllByPosition(position);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @PostMapping("/candidates/{accountId}")
    public ResponseEntity<?> createCandidate(@PathVariable Long accountId, @RequestBody Candidate candidate) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));

        Candidate candidateTemp = candidateService.createCandidate(candidate, accountId);
        candidateService.save(candidateTemp);

        return new ResponseEntity<>(candidateTemp, HttpStatus.OK);
    }

    @PutMapping("/candidates/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        Candidate candidateUpdate = candidateService.findCandidateById(id);

        if (candidateUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        candidateUpdate.setName(candidate.getName());
        candidateUpdate.setEmail(candidate.getEmail());
        candidateUpdate.setPhone(candidate.getPhone());
        candidateUpdate.setAddress(candidate.getAddress());
        candidateUpdate.setBirthday(candidate.getBirthday());
        candidateUpdate.setGender(candidate.getGender());
        candidateUpdate.setStatus(candidate.getStatus());
        candidateUpdate.setPosition(candidate.getPosition());
        candidateUpdate.setLinkLinkedin(candidate.getLinkLinkedin());
        candidateUpdate.setLinkGithub(candidate.getLinkGithub());
        candidateUpdate.setUpdatedAt(candidate.getUpdatedAt());
        candidateUpdate.setAvatar(candidate.getAvatar());
        candidateUpdate.setDescription(candidate.getDescription());
        candidateUpdate.setCertificates(candidate.getCertificates());
        candidateUpdate.setEducations(candidate.getEducations());
        candidateUpdate.setExperiences(candidate.getExperiences());
        candidateUpdate.setJobCandidates(candidate.getJobCandidates());

        candidateService.save(candidateUpdate);

        return new ResponseEntity<>(candidateUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {

            Candidate candidate = candidateService.findCandidateById(id);

            if (candidate == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            candidateService.delete(candidate.getId());

            return new ResponseEntity<>(HttpStatus.OK);
    }
}
