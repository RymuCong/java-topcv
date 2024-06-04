package com.t3h.topcv.repository.Job;

import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.entity.job.Salary_Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryJobRepository extends JpaRepository<Salary_Jobs, Long> {

    Salary_Jobs findByJob (Job job);
}
