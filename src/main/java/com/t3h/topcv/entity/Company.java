package com.t3h.topcv.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    @Column(name = "website")
    private String website;

    @Column(name = "link_facebook")
    private String linkFacebook;

    @Column(name = "link_linkedin")
    private String linkLinkedin;

    @Column(name = "size")
    private Integer size;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email_company")
    private String email;

    @Column(name = "policy")
    private String policy;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

}
