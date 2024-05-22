package com.t3h.topcv.entity.job;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "field_jobs")
public class Field_Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_jobs_id")
    private Type_Jobs typeJobs;

}
