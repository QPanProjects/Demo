package com.quanpan302.cars.payload;

import java.time.Instant;

/**
 *
 */

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private Long carCount;
    private Long storeCount;

    public UserProfile(Long id, String username, String name, Instant joinedAt, Long carCount,  Long storeCount) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.carCount = carCount;
        this.storeCount = storeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getCarCount() {
        return carCount;
    }

    public void setCarCount(Long carCount) {
        this.carCount = carCount;
    }

    public Long getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Long storeCount) {
        this.storeCount = storeCount;
    }
}
