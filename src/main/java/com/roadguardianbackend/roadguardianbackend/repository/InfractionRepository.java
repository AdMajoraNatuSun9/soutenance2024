package com.roadguardianbackend.roadguardianbackend.repository;


import com.roadguardianbackend.roadguardianbackend.entity.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfractionRepository extends JpaRepository<Infraction,Long> {
}
