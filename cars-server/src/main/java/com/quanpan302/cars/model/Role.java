package com.quanpan302.cars.model;

import com.quanpan302.cars.util.AppConstants;

import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

/**
 *
 */

@Entity
@Table(name = AppConstants.DEFAULT_TABLE_NAME_ROLES)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = AppConstants.DEFAULT_NAME_SIZE)
    private RoleName name;

    public Role() {

    }

    public Role(RoleName name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RoleName getName() { return name; }
    public void setName(RoleName name) { this.name = name; }

}
