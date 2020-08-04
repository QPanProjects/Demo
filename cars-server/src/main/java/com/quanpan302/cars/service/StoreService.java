package com.quanpan302.cars.service;

import com.quanpan302.cars.exception.BadRequestException;
import com.quanpan302.cars.exception.ResourceNotFoundException;
import com.quanpan302.cars.model.*;
import com.quanpan302.cars.payload.PagedResponse;
import com.quanpan302.cars.payload.CarRequest;
import com.quanpan302.cars.payload.CarResponse;
import com.quanpan302.cars.payload.StoreRequest;
import com.quanpan302.cars.payload.StoreResponse;
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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */

@Service
public class StoreService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    // ok
    public PagedResponse<StoreResponse> getAllStores(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Stores
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Store> stores = storeRepository.findAll(pageable);

        if (stores.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                stores.getNumber(),
                stores.getSize(), 
                stores.getTotalElements(),
                stores.getTotalPages(),
                stores.isLast());
        }

        // Map Stores to StoreResponses and store creator details
        Map<Long, User> creatorMap = getStoreByCreatorMap(stores.getContent());

        List<StoreResponse> storeResponses = stores.map(store -> {
            return ModelMapper.mapStoreToStoreResponse(
                store,
                creatorMap.get(store.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
            storeResponses,
            stores.getNumber(),
            stores.getSize(),
            stores.getTotalElements(),
            stores.getTotalPages(),
            stores.isLast());
    }

    public PagedResponse<StoreResponse> getAllStoresByUserId(Long userId, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Cars
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Store> stores = storeRepository.findByCreatedBy(userId, pageable);

        if (stores.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                Collections.emptyList(), 
                stores.getNumber(),
                stores.getSize(), 
                stores.getTotalElements(),
                stores.getTotalPages(),
                stores.isLast());
        }

        // Map Cars to CarResponses and car creator details
        Map<Long, User> creatorMap = getStoreByCreatorMap(stores.getContent());

        List<StoreResponse> storeResponses = stores.map(store -> {
            return ModelMapper.mapStoreToStoreResponse(
                store,
                creatorMap.get(store.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(
                storeResponses,
                stores.getNumber(),
                stores.getSize(),
                stores.getTotalElements(),
                stores.getTotalPages(),
                stores.isLast());
    }

    public PagedResponse<StoreResponse> getAllStoresByCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve all stores created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Page<Store> stores = storeRepository.findByCreatedBy(user.getId(), pageable);

        if (stores.getNumberOfElements() == 0) {
            return new PagedResponse<>(
                    Collections.emptyList(),
                    stores.getNumber(),
                    stores.getSize(),
                    stores.getTotalElements(),
                    stores.getTotalPages(),
                    stores.isLast());
        }

        // Map Stores to StoreResponses and store creator details
        List<StoreResponse> storeResponses = stores.map(store -> {
            return ModelMapper.mapStoreToStoreResponse(
                store,
                user);
        }).getContent();

        return new PagedResponse<>(
            storeResponses,
            stores.getNumber(),
            stores.getSize(),
            stores.getTotalElements(),
            stores.getTotalPages(),
            stores.isLast());
    }

    public Store createOneStore(StoreRequest storeRequest) {
        Store store = new Store();
        
        store.setName(storeRequest.getName());

        return storeRepository.save(store);
    }

    public StoreResponse getOneStoreByStoreId(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new ResourceNotFoundException("Store", "id", storeId));

        // Retrieve store creator details
        User creator = userRepository.findById(store.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", store.getCreatedBy()));

        return ModelMapper.mapStoreToStoreResponse(
            store,
            creator);
    }

    public PagedResponse<CarResponse> getAllCarsByStoreId(Long storeId, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Cars
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Car> cars = carRepository.findByStoreId(storeId, pageable);
        
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
        Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());

        List<CarResponse> carResponses = cars.map(car -> {
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

    public PagedResponse<CarResponse> getAllCarsByStoreIdUserId(Long storeId, Long userId, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Cars
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Car> cars = carRepository.findByStoreId(storeId, pageable);
        
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
        Map<Long, User> creatorMap = getCarByCreatorMap(cars.getContent());

        List<CarResponse> carResponses = cars.map(car -> {
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

    // ok
    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    // ok
    Map<Long, User> getStoreByCreatorMap(List<Store> stores) {
        // Get Store Creator details of the given list of cars
        List<Long> creatorIds = stores
                .stream()
                .map(Store::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }

    // ok
    Map<Long, User> getCarByCreatorMap(List<Car> cars) {
        // Get Car Creator details of the given list of cars
        List<Long> creatorIds = cars
                .stream()
                .map(Car::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
