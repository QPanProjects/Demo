package com.quanpan302.cars.controller;

import com.quanpan302.cars.util.AppConstants;
import com.quanpan302.cars.model.*;
import com.quanpan302.cars.payload.*;
import com.quanpan302.cars.repository.CarRepository;
import com.quanpan302.cars.repository.StoreRepository;
import com.quanpan302.cars.repository.UserRepository;
import com.quanpan302.cars.security.CurrentUser;
import com.quanpan302.cars.security.UserPrincipal;
import com.quanpan302.cars.service.CarService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 *
 */

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @GetMapping
    public PagedResponse<CarResponse> getCars(
        @CurrentUser UserPrincipal currentUser,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

            return carService.getAllCars(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createCarByUser(@Valid @RequestBody CarRequest carRequest) {
        Car car = carService.createOneCar(carRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{carId}")
                .buildAndExpand(car.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Car Created Successfully"));
    }

    @GetMapping("/{carId}")
    public CarResponse getCarByCarId(@PathVariable Long carId) {
        return carService.getOneCarByCarId(carId);
    }

    @GetMapping("/search/{data}")
    public PagedResponse<CarResponse> getCarBySearch(
        @CurrentUser UserPrincipal currentUser,
        @PathVariable String data,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    
            return carService.getAllCarsBySearch(data, page, size);
    }

    @GetMapping("/rank/{period}/{distance}/{fuel}")
    public PagedResponse<CarResponse> getCarByRank(
        @CurrentUser UserPrincipal currentUser,
        @PathVariable Double period, @PathVariable Double distance, @PathVariable Double fuel,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    
            return carService.getAllCarsByRank(period, distance, fuel, page, size);
    }
}
