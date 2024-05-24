package com.t3h.topcv.service;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.entity.company.Type_Company;
import com.t3h.topcv.repository.AccountRepository;
import com.t3h.topcv.repository.Company.CompanyRepository;
import com.t3h.topcv.repository.Company.TypeCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepo;

    private final AccountRepository accountRepo;

    private final TypeCompanyRepository typeCompanyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AccountRepository accountRepository, TypeCompanyRepository typeCompanyRepository) {
        this.companyRepo = companyRepository;
        this.accountRepo = accountRepository;
        this.typeCompanyRepository = typeCompanyRepository;
    }

    @Override
    @Transactional
    public void save(Company company) {
        companyRepo.save(company);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Company company = companyRepo.findById(id).orElseThrow(() -> new RuntimeException("Company not found with id " + id));

        // xóa liên kết giữa Account và Company trước khi xóa Company
        company.getAccount().setCompany(null);

        companyRepo.delete(company);
    }

    @Override
    public Company create(Company company, Long id) {

        Account account = accountRepo.findById(id).orElse(null);
        if (account == null) {
            throw new RuntimeException("Account not found with id " + id);
        }

        Type_Company typeCompany = typeCompanyRepository.findById(company.getTypeCompany().getId()).orElse(null);
        if (typeCompany == null) {
            System.out.println("lmao");
            throw new RuntimeException("Type_Company not found with id " + company.getTypeCompany().getId());
        }

        Company companyTemp = new Company();
        companyTemp.setAccount(account);
        companyTemp.setName(company.getName());
        companyTemp.setWebsite(company.getWebsite());
        companyTemp.setPhone(company.getPhone());
        companyTemp.setSize(company.getSize());
        companyTemp.setEmail(company.getEmail());
        companyTemp.setPolicy(company.getPolicy());
        companyTemp.setCreatedAt(new Date().toString());
        companyTemp.setUpdatedAt(new Date().toString());
        companyTemp.setTypeCompany(typeCompany);
        companyTemp.setJobs(company.getJobs());
        companyTemp.setNotifications(company.getNotifications());
        companyTemp.setAddressCompanies(company.getAddressCompanies());

        return companyTemp;
    }

    @Override
    public Company findById(Long id) {
        return companyRepo.findById(id).orElseThrow(() -> new RuntimeException("Company not found with id " + id));
    }

    @Override
    public List<Company> findAll() {
        return companyRepo.findAll();
    }

    @Override
    public Company findByAccountId(Long accountId) {

        Company company = companyRepo.findByAccountId(accountId);
        if (company == null) {
            throw new RuntimeException("Company not found with account id " + accountId);
        }
        return company;
    }

    @Override
    public List<Company> findBySize(Integer size) {
        return companyRepo.findBySizeLessThanEqual(size);
    }

//    @Override
//    public List<Company> findByTypeCompany(String typeCompany) {
//        return companyRepo.findByTypeCompany(typeCompany);
//    }
//
//    @Override
//    public List<Company> findByJobs(String jobs) {
//        return companyRepo.findByJobs(jobs);
//    }
//
//    @Override
//    public List<Company> findByLocation(String location) {
//        return companyRepo.findByLocation(location);
//    }
}
