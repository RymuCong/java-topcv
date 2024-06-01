package com.t3h.topcv.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.entity.job.Salary_Jobs;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinColumn(name = "job_id")
//    private Job job_id;

    @JsonManagedReference(value = "salary")
    @OneToMany(mappedBy = "salary_id", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Salary_Jobs> salary_jobs;
}
