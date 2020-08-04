package com.quanpan302.cars.payload;

import com.quanpan302.cars.util.AppConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 *
 */

public class CarRequest {
    private String model;
    private String brand;
    private String version;
    private Integer yearOfRelease;
    private Double price;
    private Double fuelConsumption;
    private Double annualMaintenanceCost;

    private Long rank;
    private Double period, distance, fuel;

    // ManyToOne
    // private String storeName;
    // Not foreign key
    private Long storeId;

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
    // 
    public Double getPeriod() { return period; }
    public Double getDistance() { return distance; }
    public Double getFuel() { return fuel; }
}
