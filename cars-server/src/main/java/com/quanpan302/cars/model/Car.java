package com.quanpan302.cars.model;

import com.quanpan302.cars.model.audit.UserDateAudit;
// import org.hibernate.annotations.BatchSize;
// import org.hibernate.annotations.Fetch;
// import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Size;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Objects;

/**
 *
 */

@Entity
@Table(name = "cars")
public class Car extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String version;
    private Integer yearOfRelease;
    private Double price;
    private Double fuelConsumption;
    private Double annualMaintenanceCost;

    @Column(nullable = true)
    private Long rank;

    // // ManyToOne
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "store_id")
    // private Store store;
    // Not foreign key
    private Long storeId;

    public Car() {

    }

    // // ManyToOne
    // public Car(String brand, String model, String version, Integer yearOfRelease,
    //            Double price, Double fuelConsumption, Double annualMaintenanceCost,
    //            Store store) {
    //     this.brand = brand;
    //     this.model = model;
    //     this.version = version;
    //     this.yearOfRelease = yearOfRelease;
    //     this.price = price;
    //     this.fuelConsumption = fuelConsumption;
    //     this.annualMaintenanceCost = annualMaintenanceCost;

    //     this.store = store;
    // }

    // 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // 
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public Integer getYearOfRelease() { return yearOfRelease; }
    public void setYearOfRelease(Integer yearOfRelease) { this.yearOfRelease = yearOfRelease; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Double getFuelConsumption() { return fuelConsumption; }
    public void setFuelConsumption(Double fuelConsumption) { this.fuelConsumption = fuelConsumption; }

    public Double getAnnualMaintenanceCost() { return annualMaintenanceCost; }
    public void setAnnualMaintenanceCost(Double annualMaintenanceCost) { this.annualMaintenanceCost = annualMaintenanceCost; }

    // // ManyToOne
    // public Store getStore() { return store; }
    // public void setStore(Store store) { this.store = store; }
    // Not foreign key
    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                '}';
    }
}
