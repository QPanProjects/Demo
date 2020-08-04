package com.quanpan302.cars.controller;

import com.quanpan302.cars.model.*;
import com.quanpan302.cars.payload.*;
import com.quanpan302.cars.repository.CarRepository;
import com.quanpan302.cars.repository.StoreRepository;
import com.quanpan302.cars.repository.UserRepository;
import com.quanpan302.cars.security.CurrentUser;
import com.quanpan302.cars.security.UserPrincipal;
import com.quanpan302.cars.service.StoreService;
import com.quanpan302.cars.util.AppConstants;

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
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @GetMapping
    public PagedResponse<StoreResponse> getStores(
        @CurrentUser UserPrincipal currentUser,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

            return storeService.getAllStores(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createStoreByUser(@Valid @RequestBody StoreRequest storeRequest) {
        Store store = storeService.createOneStore(storeRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{storeId}")
                .buildAndExpand(store.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Store Created Successfully"));
    }

    @GetMapping("/{storeId}")
    public StoreResponse getStoreById(
        @CurrentUser UserPrincipal currentUser,
        @PathVariable Long storeId) {
        
            return storeService.getOneStoreByStoreId(storeId);
    }

    @GetMapping("/{storeId}/cars")
    public PagedResponse<CarResponse>  getCarsByStoreId(
        @CurrentUser UserPrincipal currentUser,
        @PathVariable Long storeId,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        
            return storeService.getAllCarsByStoreId(storeId, page, size);
    }

}
