package com.roadguardianbackend.roadguardianbackend.repository;


import com.roadguardianbackend.roadguardianbackend.entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur,Long> {
}
