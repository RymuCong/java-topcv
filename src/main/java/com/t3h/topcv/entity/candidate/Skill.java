package com.t3h.topcv.entity.candidate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.t3h.topcv.entity.job.Level_Job;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

//    @Column(name = "level")
//    private String level;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "candidate_id")
    private Candidate candidateId;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "level_job_id")
    private Level_Job levelJobs;
}
