package com.t3h.topcv.entity;

import com.t3h.topcv.entity.company.Address_Company;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "location")
public class Location {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "address")
        private String address;

        @Column(name = "city")
        private String city;

        @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
        private List<Address_Company> addressCompany;
}
