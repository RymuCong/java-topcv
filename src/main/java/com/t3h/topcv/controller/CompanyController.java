package com.t3h.topcv.controller;

import com.t3h.topcv.dto.CompanyResponse;
import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies/getAll")
    public ResponseEntity<?> getAllCompany() {
        List<Company> companies = companyService.findAll();
        CompanyResponse response = new CompanyResponse();
        response.setMessage("success");
        response.setData(companies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/companies/getInfor")
    public ResponseEntity<?> getCompanyInfor(String email) {
        List <Company> company = companyService.findByEmail(email);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CompanyResponse response = new CompanyResponse();
        response.setMessage("success");
        response.setData(company);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/companies/account/{accountId}")
    public ResponseEntity<?> getCompanyByAccountId(@PathVariable Long accountId) {
        Company company = companyService.findByAccountId(accountId);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/companies/size/{size}")
    public ResponseEntity<?> getCompanyBySize(@PathVariable Integer size) {
        List<Company> companies = companyService.findBySize(size);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping("/companies/{id}")
    public ResponseEntity<?> createCompany(@PathVariable Long id, @RequestBody Company company) {

        Company companyTemp = companyService.create(company, id);

        companyService.save(companyTemp);

        return new ResponseEntity<>(companyTemp, HttpStatus.OK);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id,@RequestBody Company company) {

        Company companyTemp = companyService.findById(id);

        if (companyTemp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        companyTemp.setName(company.getName());
        companyTemp.setWebsite(company.getWebsite());
        companyTemp.setPhone(company.getPhone());
        companyTemp.setSize(company.getSize());
        companyTemp.setEmail(company.getEmail());
        companyTemp.setUpdatedAt(company.getUpdatedAt());
        companyTemp.setTypeCompany(company.getTypeCompany());
        companyTemp.setJobs(company.getJobs());
        companyTemp.setNotifications(company.getNotifications());
        companyTemp.setAddressCompanies(company.getAddressCompanies());

        companyService.save(companyTemp);

        return new ResponseEntity<>(companyTemp, HttpStatus.OK);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {

        Company company = companyService.findById(id);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
