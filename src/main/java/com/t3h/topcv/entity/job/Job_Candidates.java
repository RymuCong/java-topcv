package com.t3h.topcv.entity.job;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.t3h.topcv.entity.candidate.Candidate;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "job_candidates")
public class Job_Candidates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "cv_url")
    private String cv_url;

    @Column(name = "interview_day")
    private String interview_day;

    @Column(name = "content")
    private String content;

    @JsonBackReference(value = "jobCandidates1")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "job_id")
    private Job job_id;

    @JsonBackReference(value = "jobCandidates2")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "candidate_id")
    private Candidate candidate_id;

}
