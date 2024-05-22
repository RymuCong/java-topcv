package com.t3h.topcv.entity;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.company.Company;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidateId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company companyId;

}
