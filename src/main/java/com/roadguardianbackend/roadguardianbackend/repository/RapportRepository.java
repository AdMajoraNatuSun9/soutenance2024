package com.roadguardianbackend.roadguardianbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roadguardianbackend.roadguardianbackend.entity.Rapport;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportRepository extends JpaRepository<Rapport,Long>{
    
}
