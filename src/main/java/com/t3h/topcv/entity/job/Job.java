package com.t3h.topcv.entity.job;

import com.fasterxml.jackson.annotation.*;
import com.t3h.topcv.entity.company.Address_Company;
import com.t3h.topcv.entity.company.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "salary")
    private String salary;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "requirement", length = 10000)
    private String requirement;

    @Column(name = "expired_at")
    private Date expiredAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private String status;

//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "company_id")
    private Company companyId;

    @JsonManagedReference
    @OneToMany( mappedBy = "job_id",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Type_Jobs> typeJobs;

    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address_company_id")
    private Address_Company addressCompanyId;

    @JsonManagedReference
    @OneToMany(mappedBy = "job", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Level_Job_Detail> levelJobId;

    @JsonBackReference
    @OneToMany(mappedBy = "job_id", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Job_Candidates> jobCandidates;

    @JsonManagedReference
    @OneToMany(mappedBy = "job_id", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List <Salary_Jobs> salaryJobs;

}
