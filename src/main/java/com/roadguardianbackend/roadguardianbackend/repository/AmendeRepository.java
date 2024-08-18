package com.roadguardianbackend.roadguardianbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roadguardianbackend.roadguardianbackend.entity.Amende;
import org.springframework.stereotype.Repository;

@Repository
public interface AmendeRepository extends JpaRepository<Amende,Long> {
    
}
