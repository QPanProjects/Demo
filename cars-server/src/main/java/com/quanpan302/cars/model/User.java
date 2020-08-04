package com.quanpan302.cars.model;

import com.quanpan302.cars.util.AppConstants;
import com.quanpan302.cars.model.audit.DateAudit;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Entity
@Table(name = AppConstants.DEFAULT_TABLE_NAME_USERS, uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "username"
    }),
    @UniqueConstraint(columnNames = {
        "email"
    })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = AppConstants.DEFAULT_NAME_SIZE)
    private String name;

    @NotBlank
    @Size(max = AppConstants.DEFAULT_USERNAME_SIZE)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = AppConstants.DEFAULT_EMAIL_SIZE)
    @Email
    private String email;

    @NotBlank
    @Size(max = AppConstants.DEFAULT_PASSWORD_SIZE)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        // One user can have many roles, but not "ADMIN"
        name = AppConstants.DEFAULT_TABLE_NAME_USER_ROLESS,
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}