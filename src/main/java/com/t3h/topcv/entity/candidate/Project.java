package com.t3h.topcv.entity.candidate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "description")
    private String description;

    @Column(name = "start_at")
    private String startAt;

    @Column(name = "end_at")
    private String endAt;

    @Column(name = "status")
    private Integer status;

    @JsonBackReference(value = "projects")
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidateId;

}
