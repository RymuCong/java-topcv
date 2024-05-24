package com.t3h.topcv.controller.Candidate;

import com.t3h.topcv.entity.candidate.Certificate;
import com.t3h.topcv.exception.ResourceNotFoundException;
import com.t3h.topcv.repository.Candidate.CertificateRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CertificateController {

    private final CertificateRepository certificateRepo;

    private final CandidateService candidateService;

    @Autowired
    public CertificateController(CertificateRepository certificateRepo, CandidateService candidateService) {
        this.certificateRepo = certificateRepo;
        this.candidateService = candidateService;
    }

     @GetMapping("/certificates")
    public List<Certificate> getAllCertificates() {
        return certificateRepo.findAll();
    }

    @GetMapping("/certificates/{id}")
    public Certificate getCertificateById(@PathVariable Long id) {
        return certificateRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with id " + id));
    }

    @GetMapping("/certificates/candidate/{candidateId}")
    public List<Certificate> getCertificatesByCandidateId(@PathVariable Long candidateId) {
        return certificateRepo.findByCandidateId(candidateService.findCandidateById(candidateId));
    }

    @PostMapping("/certificates")
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateRepo.save(certificate);
    }

    @PutMapping("/certificates/{id}")
    public Certificate updateCertificate(@PathVariable Long id, @RequestBody Certificate certificate) {
        Certificate certificateTemp = certificateRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with id " + id));
        certificateTemp.setName(certificate.getName());
        certificateTemp.setDescription(certificate.getDescription());
        certificateTemp.setStartAt(certificate.getStartAt());
        certificateTemp.setEndAt(certificate.getEndAt());
        certificateTemp.setOrganization(certificate.getOrganization());
        certificateTemp.setStatus(certificate.getStatus());
        certificateTemp.setCandidateId(certificate.getCandidateId());
        return certificateRepo.save(certificateTemp);
    }

    @DeleteMapping("/certificates/{id}")
    public void deleteCertificate(@PathVariable Long id) {
        certificateRepo.deleteById(id);
    }
}
