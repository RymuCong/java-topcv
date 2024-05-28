package com.t3h.topcv.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.company.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Entity
@Table(name = "account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "full_name")
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Candidate candidate;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public Account() {
    }

    public Account(String userName, String password, Integer status) {
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public Account(String userName, String password, Integer status,
                Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }
}