package com.quanpan302.cars.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 *
 */

public class CarResponse {
    private Long id;

    private String model;
    private String brand;
    private String version;
    private Integer yearOfRelease;
    private Double price;
    private Double fuelConsumption;
    private Double annualMaintenanceCost;

    private UserSummary createdBy;
    private Instant creationDateTime;

    private Long rank;

    // ManyToOne
    // private String storeName;
    // Not foreign key
    private Long storeId;

    // 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // 
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

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

    public UserSummary getCreatedBy() { return createdBy; }
    public void setCreatedBy(UserSummary createdBy) { this.createdBy = createdBy; }

    public Instant getCreationDateTime() { return creationDateTime; }
    public void setCreationDateTime(Instant creationDateTime) { this.creationDateTime = creationDateTime; }

    // ManyToOne
    // public String getStoreName() { return storeName; }
    // public void setStoreName(String storeName) { this.storeName = storeName; }
    // Not foreign key
    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    // 
    public Long getRank() { return rank; }
    public void setRank(Long rank) { 
        this.rank = rank; 
    }

}
