package com.quanpan302.cars.repository;

import com.quanpan302.cars.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 */

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findById(Long carId);

    Page<Car> findByCreatedBy(Long userId, Pageable pageable);

    Page<Car> findByBrand(String brand, Pageable pageable);
    Page<Car> findByCreatedByAndBrand(Long userId, String brand, Pageable pageable);

    Page<Car> findByModel(String model, Pageable pageable);
    Page<Car> findByCreatedByAndModel(Long userId, String model, Pageable pageable);

    Page<Car> findByYearOfRelease(Integer yearOfRelease, Pageable pageable);
    Page<Car> findByCreatedByAndYearOfRelease(Long userId, Integer yearOfRelease, Pageable pageable);

    Page<Car> findByStoreId(Long storeId, Pageable pageable);
    Page<Car> findByCreatedByAndStoreId(Long userId, String storeId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Car> findByIdIn(List<Long> carIds);

    List<Car> findByIdIn(List<Long> carIds, Sort sort);
}
