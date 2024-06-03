package com.t3h.topcv.repository.Company;

import com.t3h.topcv.entity.company.Address_Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressCompanyRepository extends JpaRepository<Address_Company, Long> {
}
