package com.quanpan302.cars.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 *
 */

public class StoreResponse {
    private long id;
    private String name;

    private List<CarResponse> cars;

    private UserSummary createdBy;
    private Instant creationDateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long selectedCar;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<CarResponse> getCars() { return cars; }
    public void setCars(List<CarResponse> cars) { this.cars = cars; }

    public UserSummary getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }
    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    // public Long getSelectedCar() {
    //     return selectedCar;
    // }
    // public void setSelectedCar(Long selectedCar) {
    //     this.selectedCar = selectedCar;
    // }

    public Long getSelectedCarByStoreID() {
        return selectedCar;
    }
    public void getSelectedCarByStoreID(Long selectedCar) {
        this.selectedCar = selectedCar;
    }
        
}
