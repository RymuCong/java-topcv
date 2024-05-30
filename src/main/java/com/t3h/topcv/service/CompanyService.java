package com.t3h.topcv.service;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.company.Company;

import java.util.List;

public interface CompanyService {

    void save(Company company);

    void delete(Long id);

    Company create(Company company, Long id);

    Company findById(Long id);

    List <Company> findAll();

    Company findByAccountId(Long accountId);

    List <Company> findBySize(Integer size);

    List <Company> findByEmail(String email);

//    List <Company> findByTypeCompany(String typeCompany);
//
//    List <Company> findByJobs(String jobs);
//
//    List <Company> findByLocation(String location);
}
