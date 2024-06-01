package com.t3h.topcv.entity.candidate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    private String major;

    @Column(name = "start_at")
    private String startAt;

    @Column(name = "end_at")
    private String endAt;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;

    @JsonBackReference(value = "educations")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "candidate_id")
    private Candidate candidateId;
}
