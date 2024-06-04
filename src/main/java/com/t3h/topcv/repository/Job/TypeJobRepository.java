package com.t3h.topcv.repository.Job;

import com.t3h.topcv.entity.job.Type_Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeJobRepository extends JpaRepository<Type_Jobs, Long> {
}
