package com.t3h.topcv.entity.candidate;

import com.fasterxml.jackson.annotation.*;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.job.Job_Candidates;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    private Integer status;

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

    @Lob
    @Column(name = "about_me")
    private String aboutMe;

    @JsonManagedReference(value = "certificates")
    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;

    @JsonManagedReference(value = "educations")
    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @JsonManagedReference(value = "experiences")
    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    @JsonManagedReference(value = "jobCandidates2")
    @OneToMany(mappedBy = "candidate_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job_Candidates> jobCandidates;

    @JsonManagedReference(value = "projects")
    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    @JsonManagedReference(value = "skills")
    @OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonProperty("account_id")
    public Long getAccountId() {
        return account != null ? account.getId() : null;
    }

}
