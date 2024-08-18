package com.roadguardianbackend.roadguardianbackend.repository;


import com.roadguardianbackend.roadguardianbackend.entity.ControleRoutier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControleRoutierRepository extends JpaRepository<ControleRoutier,Long> {
}
