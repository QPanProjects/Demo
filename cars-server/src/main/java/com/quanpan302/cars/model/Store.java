package com.quanpan302.cars.model;

import com.quanpan302.cars.util.AppConstants;
import com.quanpan302.cars.model.audit.UserDateAudit;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */

@Entity
@Table(name = AppConstants.DEFAULT_TABLE_NAME_STORES)
public class Store extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = AppConstants.DEFAULT_NAME_SIZE)
    private String name;

    // // OneToMany
    // @OneToMany(
    //     targetEntity = Car.class,
    //     mappedBy = "store",
    //     cascade = CascadeType.ALL,
    //     fetch = FetchType.EAGER,
    //     orphanRemoval = true
    // )
    // @Size(min = AppConstants.DB_SELECT_LIST_BND_MIN, max = AppConstants.DB_SELECT_LIST_BND_MAX)
    // @Fetch(FetchMode.SELECT)
    // @BatchSize(size = AppConstants.DB_SELECT_BATCH_SIZE)
    // private List<Car> cars = new ArrayList<>();

    public Store() {
    }

    public Store(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // // OneToMany
    // public List<Car> getCars() { 
    //     return cars; 
    // }
    // public void setCars(List<Car> cars) { this.cars = cars; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
