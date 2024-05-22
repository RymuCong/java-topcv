package com.t3h.topcv.entity.candidate;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.job.Job;
import com.t3h.topcv.entity.job.Job_Candidates;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "status")
    private String status;

    @Column(name = "position")
    private String position;

    @Column(name = "link_linkedin")
    private String linkLinkedin;

    @Column(name = "link_github")
    private String linkGithub;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "candidate_id", cascade = CascadeType.ALL)
    private List <Job_Candidates> jobCandidates;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

}
