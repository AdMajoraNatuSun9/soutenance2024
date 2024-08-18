package com.roadguardianbackend.roadguardianbackend.repository;


import com.roadguardianbackend.roadguardianbackend.entity.Infraction;
import com.roadguardianbackend.roadguardianbackend.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
}
