package com.t3h.topcv.repository.Job;


import com.t3h.topcv.entity.job.Field_Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldJobRepository extends JpaRepository<Field_Job, Long>{
}
