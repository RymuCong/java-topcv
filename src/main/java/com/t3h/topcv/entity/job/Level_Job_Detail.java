package com.t3h.topcv.entity.job;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "level_job_detail")
public class Level_Job_Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "level_job_id")
    private Level_Job levelJobs;
}
