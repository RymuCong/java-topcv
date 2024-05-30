package com.t3h.topcv.repository.Company;

import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.entity.company.Type_Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByAccountId(Long accountId);

    List<Company> findBySizeLessThanEqual(Integer size);

    List<Company> findByEmail(String email);

//    List<Company> findByTypeCompany(Type_Company typeCompany);

//    List<Company> findByJobs(String job);

//    List<Company> findByLocation(String location);
}
