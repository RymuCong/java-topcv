package com.t3h.topcv.repository.Job;

import com.t3h.topcv.entity.job.Level_Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelJobRepository extends JpaRepository<Level_Job, Long> {
}
