package com.t3h.topcv.controller.Company;

import com.t3h.topcv.dto.CompanyRequest;
import com.t3h.topcv.dto.CompanyResponse;
import com.t3h.topcv.dto.SingleResponse;
import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.repository.Company.TypeCompanyRepository;
import com.t3h.topcv.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    private final AccountRepository accountRepo;

    private final TypeCompanyRepository typeCompanyRepo;

    @Autowired
    public CompanyController(CompanyService companyService, AccountRepository accountRepo, TypeCompanyRepository typeCompanyRepo) {
        this.companyService = companyService;
        this.accountRepo = accountRepo;
        this.typeCompanyRepo = typeCompanyRepo;
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
    public ResponseEntity<?> getCompanyInfor(@AuthenticationPrincipal UserDetails userDetails) {
        Company company = companyService.findByAccountId(accountRepo.findByUserName(userDetails.getUsername()).getId());

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SingleResponse response = new SingleResponse();
        response.setMessage("success");
        response.setData(company);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
   @GetMapping("/companies/getInfoCompanyById/{id}")
    public ResponseEntity<?> getCompanyInfoById(@PathVariable Long id) {
        Company company = companyService.findById(id);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SingleResponse response = new SingleResponse();
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

    @PatchMapping("/companies/update-info/{id}")
    public ResponseEntity<?> updateCompanyInfo(@PathVariable Long id ,@RequestBody Map<String, String> company) {

        Company companyTemp = companyService.findById(id);

        if (companyTemp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        companyTemp.setPhone(company.get("phone"));
        companyTemp.setWebsite(company.get("website"));
        companyTemp.setUpdatedAt(new Date().toString());
        companyTemp.setLogo(company.get("photo"));
        companyTemp.setDescription(company.get("description"));
        companyTemp.setLinkFacebook(company.get("link_facebook"));
        companyTemp.setPolicy(company.get("policy"));
        companyTemp.setEmail(company.get("email"));
        companyTemp.setSize(Integer.valueOf(company.get("size")));
        companyTemp.setTypeCompany(typeCompanyRepo.findById(Long.valueOf(company.get("typeCompany_id"))).orElse(null));

        companyService.updateInfo(companyTemp, id);

        SingleResponse response = new SingleResponse();
        response.setMessage("Update company info successfully");
        response.setData(companyTemp);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
