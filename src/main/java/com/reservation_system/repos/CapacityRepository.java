package com.reservation_system.repos;

import com.reservation_system.model.AmenityType;
import com.reservation_system.model.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {
    static AmenityType findByAmenityType(AmenityType amenityType) {
        return amenityType;
    }
}