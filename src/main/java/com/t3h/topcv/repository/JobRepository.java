package com.t3h.topcv.repository;

import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.entity.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByCompanyId(Company companyId);

    List<Job> findTop6ByOrderByIdDesc();

    List<Job> findAllByStatus(String status);
}
