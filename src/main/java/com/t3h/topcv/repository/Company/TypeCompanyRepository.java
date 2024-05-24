package com.t3h.topcv.repository.Company;

import com.t3h.topcv.entity.company.Type_Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCompanyRepository extends JpaRepository<Type_Company, Long> {
}
