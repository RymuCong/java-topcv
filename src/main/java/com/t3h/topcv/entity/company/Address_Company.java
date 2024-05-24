package com.t3h.topcv.entity.company;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address_company")
public class Address_Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "map_url")
    private String map_url;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "company_id")
    private Company companyId;

//    @OneToMany(mappedBy = "addressId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    private List <Job> job;
}
