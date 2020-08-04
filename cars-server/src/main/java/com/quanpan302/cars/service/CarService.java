package com.quanpan302.cars.service;

import com.quanpan302.cars.exception.BadRequestException;
import com.quanpan302.cars.exception.ResourceNotFoundException;
import com.quanpan302.cars.model.*;
import com.quanpan302.cars.payload.PagedResponse;
import com.quanpan302.cars.payload.CarRequest;
import com.quanpan302.cars.payload.CarResponse;
import com.quanpan302.cars.repository.CarRepository;
import com.quanpan302.cars.repository.StoreRepository;
import com.quanpan302.cars.repository.UserRepository;
import com.quanpan302.cars.security.UserPrincipal;
import com.quanpan302.cars.util.AppConstants;
import com.quanpan302.cars.util.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    // ok
    public PagedResponse<CarResponse> getAllCars(final UserPrincipal currentUser, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Cars
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "brand");

        final Page<Car> cars = carRepository.findAll(pageable);

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                cars.getNumber(),
                cars.getSize(), 
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
        }

        // Map Cars to CarResponses and store creator details
        final Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());

        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarToCarResponse(
                car,
                creatorMap.get(car.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
            carResponses,
            cars.getNumber(),
            cars.getSize(),
            cars.getTotalElements(),
            cars.getTotalPages(),
            cars.isLast());
    }

    public PagedResponse<CarResponse> getAllCarsByUserId(final Long userId, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Cars
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "brand");

        final Page<Car> cars = carRepository.findByCreatedBy(userId, pageable);

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                cars.getNumber(),
                cars.getSize(), 
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
        }

        // Map Cars to CarResponses and car creator details
        final Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());

        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarToCarResponse(
                car,
                creatorMap.get(car.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
                carResponses,
                cars.getNumber(),
                cars.getSize(),
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
    }

    public PagedResponse<CarResponse> getAllCarsByCreatedBy(final String username, final UserPrincipal currentUser, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        final User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all cars created by the given username
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "brand");

        final Page<Car> cars = carRepository.findByCreatedBy(user.getId(), pageable);

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                    Collections.emptyList(),
                    cars.getNumber(),
                    cars.getSize(),
                    cars.getTotalElements(),
                    cars.getTotalPages(),
                    cars.isLast());
        }

        // Map Cars to CarResponses and car creator details
        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarToCarResponse(
                    car,
                    user);
        }).getContent();

        return new PagedResponse<>(
                carResponses,
                cars.getNumber(),
                cars.getSize(),
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
    }

    public PagedResponse<CarResponse> getAllCarsBySearch(final String data, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve all cars created by the given username
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "rank");

        Page<Car> cars = carRepository.findByBrand(data, pageable);
        if (cars.getNumberOfElements() == 0) {
            cars = carRepository.findByModel(data, pageable);
        }
        if (cars.getNumberOfElements() == 0) {
            cars = carRepository.findByYearOfRelease(Integer.parseInt(data), pageable);
        }

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                cars.getNumber(),
                cars.getSize(), 
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
        }

        // Map Cars to CarResponses and store creator details
        final Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());
        final Double period = 4.0;
        final Double distance = 250.0;
        final Double fuel = 0.66;

        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarRankToCarResponse(
                car,
                period,
                distance,
                fuel,
                creatorMap.get(car.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
            carResponses,
            cars.getNumber(),
            cars.getSize(),
            cars.getTotalElements(),
            cars.getTotalPages(),
            cars.isLast());
    }

    public PagedResponse<CarResponse> getAllCarsByCreatedBySearch(final String username, final String data, final UserPrincipal currentUser, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        final User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all cars created by the given username
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "brand");

        Page<Car> cars = carRepository.findByCreatedByAndBrand(user.getId(), data, pageable);
        if (cars.getNumberOfElements() == 0) {
            cars = carRepository.findByCreatedByAndModel(user.getId(), data, pageable);
        }
        if (cars.getNumberOfElements() == 0) {
            cars = carRepository.findByCreatedByAndYearOfRelease(user.getId(), Integer.parseInt(data), pageable);
        }

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                    Collections.emptyList(),
                    cars.getNumber(),
                    cars.getSize(),
                    cars.getTotalElements(),
                    cars.getTotalPages(),
                    cars.isLast());
        }

        // Map Cars to CarResponses and car creator details
        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarToCarResponse(
                    car,
                    user);
        }).getContent();

        return new PagedResponse<>(
                carResponses,
                cars.getNumber(),
                cars.getSize(),
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
    }

    public PagedResponse<CarResponse> getAllStoresByCreatedBy(final String data, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve all cars created by the given username
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "model");

        final Page<Car> cars = carRepository.findByBrand(data, pageable);

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                cars.getNumber(),
                cars.getSize(), 
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
        }

        // Map Cars to CarResponses and store creator details
        final Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());
        final Double period = 4.0;
        final Double distance = 250.0;
        final Double fuel = 0.66;

        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarRankToCarResponse(
                car,
                period,
                distance,
                fuel,
                creatorMap.get(car.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
            carResponses,
            cars.getNumber(),
            cars.getSize(),
            cars.getTotalElements(),
            cars.getTotalPages(),
            cars.isLast());
    }

    public PagedResponse<CarResponse> getAllCarsByRank(final Double period, final Double distance, final Double fuel, final int page, final int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve all cars created by the given username
        final Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "rank");

        final Page<Car> cars = carRepository.findAll(pageable);

        if (cars.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                cars.getNumber(),
                cars.getSize(), 
                cars.getTotalElements(),
                cars.getTotalPages(),
                cars.isLast());
        }
        
        // Map Cars to CarResponses and store creator details
        final Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());

        final List<CarResponse> carResponses = cars.map(car -> {
            return ModelMapper.mapCarRankToCarResponse(
                car,
                period,
                distance,
                fuel,
                creatorMap.get(car.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
            carResponses,
            cars.getNumber(),
            cars.getSize(),
            cars.getTotalElements(),
            cars.getTotalPages(),
            cars.isLast());
    }

    public CarResponse getOneCarByCarId(final Long carId) {
        final Car car = carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("Car", "id", carId));

        // Retrieve car creator details
        final User creator = userRepository.findById(car.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", car.getCreatedBy()));

        return ModelMapper.mapCarToCarResponse(
                car,
                creator);
    }

    public Car createOneCar(final CarRequest carRequest) {
        // // ManyToOne
        // final Car car = new Car();

        // car.setBrand(carRequest.getBrand());
        // car.setModel(carRequest.getModel());
        // car.setVersion(carRequest.getVersion());
        // car.setYearOfRelease(carRequest.getYearOfRelease());
        // car.setPrice(carRequest.getPrice());
        // car.setFuelConsumption(carRequest.getFuelConsumption());
        // car.setAnnualMaintenanceCost(carRequest.getAnnualMaintenanceCost());

        // car.setStore(new Store(carRequest.getStoreName()));

        // return carRepository.save(car);

        // Not foreign key
        final Car car = new Car();

        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setVersion(carRequest.getVersion());
        car.setYearOfRelease(carRequest.getYearOfRelease());
        car.setPrice(carRequest.getPrice());
        car.setFuelConsumption(carRequest.getFuelConsumption());
        car.setAnnualMaintenanceCost(carRequest.getAnnualMaintenanceCost());

        car.setStoreId(carRequest.getStoreId());

        return carRepository.save(car);
    }

    // page
    private void validatePageNumberAndSize(final int page, final int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    // join
    Map<Long, User> getCarByCreatorMap(final List<Car> cars) {
        // Get Car Creator details of the given list of cars
        final List<Long> creatorIds = cars
                .stream()
                .map(Car::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        final List<User> creators = userRepository.findByIdIn(creatorIds);
        final Map<Long, User> creatorMap = creators
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
