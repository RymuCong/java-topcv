package com.t3h.topcv.entity;

import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.company.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Candidate candidate;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
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