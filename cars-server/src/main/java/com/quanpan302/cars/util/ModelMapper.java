package com.quanpan302.cars.util;

import com.quanpan302.cars.model.Car;
import com.quanpan302.cars.model.Store;
import com.quanpan302.cars.model.User;
import com.quanpan302.cars.payload.StoreResponse;
import com.quanpan302.cars.payload.CarRequest;
import com.quanpan302.cars.payload.CarResponse;
import com.quanpan302.cars.payload.UserSummary;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */

public class ModelMapper {

    public static CarResponse mapCarToCarResponse(Car car, User creator) {
        CarResponse carResponse = new CarResponse();
        CarRequest carRequest = new CarRequest();
        // 
        carResponse.setId(car.getId());

        carResponse.setBrand(car.getBrand());
        carResponse.setModel(car.getModel());
        carResponse.setVersion(car.getVersion());
        carResponse.setYearOfRelease(car.getYearOfRelease());
        carResponse.setPrice(car.getPrice());
        carResponse.setFuelConsumption(car.getFuelConsumption());
        carResponse.setAnnualMaintenanceCost(car.getAnnualMaintenanceCost());

        carResponse.setStoreId(car.getStoreId());

        carResponse.setCreationDateTime(car.getCreatedAt());
        // 
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        carResponse.setCreatedBy(creatorSummary);

        return carResponse;
    }

    public static CarResponse mapCarRankToCarResponse(Car car, Double period, Double distance, Double fuel, User creator) {
        CarResponse carResponse = new CarResponse();
        // 
        carResponse.setId(car.getId());

        carResponse.setBrand(car.getBrand());
        carResponse.setModel(car.getModel());
        carResponse.setVersion(car.getVersion());
        carResponse.setYearOfRelease(car.getYearOfRelease());
        carResponse.setPrice(car.getPrice());
        carResponse.setFuelConsumption(car.getFuelConsumption());
        carResponse.setAnnualMaintenanceCost(car.getAnnualMaintenanceCost());

        carResponse.setStoreId(car.getStoreId());

        carResponse.setCreationDateTime(car.getCreatedAt());
        // 
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        carResponse.setCreatedBy(creatorSummary);

        //
        Double rank;
        Double price, fuelConsumption, annualMaintenanceCost;

        price = car.getPrice();
        fuelConsumption = car.getFuelConsumption();
        annualMaintenanceCost = car.getAnnualMaintenanceCost();

        rank = (distance / fuelConsumption * fuel + annualMaintenanceCost / 12.0) * period - price;

        System.out.println("rank (carId=" + car.getId() + ", " + car.getBrand() + ", " + car.getModel() + ", " + car.getYearOfRelease() +  "): " + rank);
        carResponse.setRank(rank.longValue());

        return carResponse;
    }

    public static StoreResponse mapStoreToStoreResponse(Store store, User creator) {
        StoreResponse storeResponse = new StoreResponse();
        // 
        storeResponse.setId(store.getId());
        storeResponse.setName(store.getName());

        storeResponse.setCreationDateTime(store.getCreatedAt());
        // 
        // List<CarResponse> carResponses = store.getCars().stream().map(car -> {
        //     CarResponse carResponse = new CarResponse();

        //     carResponse.setBrand(car.getBrand());
        //     carResponse.setModel(car.getModel());
        //     carResponse.setVersion(car.getVersion());
        //     carResponse.setYearOfRelease(car.getYearOfRelease());
        //     carResponse.setPrice(car.getPrice());
        //     carResponse.setFuelConsumption(car.getFuelConsumption());
        //     carResponse.setAnnualMaintenanceCost(car.getAnnualMaintenanceCost());

        //     return carResponse;
        // }).collect(Collectors.toList());
        // storeResponse.setCars(carResponses);
        // 
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        storeResponse.setCreatedBy(creatorSummary);

        return storeResponse;
    }
}
