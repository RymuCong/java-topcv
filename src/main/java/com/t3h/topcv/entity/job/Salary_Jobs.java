package com.t3h.topcv.entity.job;

import com.t3h.topcv.entity.Salary;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "salary_jobs")
public class Salary_Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "salary_id")
    private Salary salary_id;
}
