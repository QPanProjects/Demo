package com.quanpan302.cars.repository;

import com.quanpan302.cars.model.Store;
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
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findById(Long storeId);

    Page<Store> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Store> findByIdIn(List<Long> storeIds);

    List<Store> findByIdIn(List<Long> storeIds, Sort sort);
}
