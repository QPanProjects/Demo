package com.quanpan302.cars.controller;

import com.quanpan302.cars.exception.ResourceNotFoundException;
import com.quanpan302.cars.model.User;
import com.quanpan302.cars.payload.*;
import com.quanpan302.cars.repository.CarRepository;
import com.quanpan302.cars.repository.StoreRepository;
import com.quanpan302.cars.repository.UserRepository;
import com.quanpan302.cars.security.UserPrincipal;
import com.quanpan302.cars.service.CarService;
import com.quanpan302.cars.service.StoreService;
import com.quanpan302.cars.security.CurrentUser;
import com.quanpan302.cars.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private StoreService storeService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users")
    public PagedResponse<CarResponse> getUsers(
        @CurrentUser UserPrincipal currentUser,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

            return carService.getAllCars(currentUser, page, size);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long carCount = carRepository.countByCreatedBy(user.getId());
        long storeCount = storeRepository.countByCreatedBy(user.getId());

        UserProfile userProfile = new UserProfile(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getCreatedAt(),
                carCount,
                storeCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/cars")
    public PagedResponse<CarResponse> getCarsCreatedBy(
        @PathVariable(value = "username") String username,
        @CurrentUser UserPrincipal currentUser,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

            return carService.getAllCarsByCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users/{username}/cars/search/{data}")
    public PagedResponse<CarResponse> getCarsCreatedBySearh(
        @PathVariable(value = "username") String username,
        @PathVariable(value = "data") String data,
        @CurrentUser UserPrincipal currentUser,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

            return carService.getAllCarsByCreatedBySearch(username, data, currentUser, page, size);
    }

    @GetMapping("/users/{username}/stores")
    public PagedResponse<StoreResponse> getStoresCreatedBy(
        @PathVariable(value = "username") String username,
        @CurrentUser UserPrincipal currentUser,
        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        
            return storeService.getAllStoresByCreatedBy(username, currentUser, page, size);
    }
}
