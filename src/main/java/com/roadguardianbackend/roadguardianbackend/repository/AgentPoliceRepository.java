package com.roadguardianbackend.roadguardianbackend.repository;

import com.roadguardianbackend.roadguardianbackend.entity.AgentPolice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentPoliceRepository  extends JpaRepository<AgentPolice,Long> {
}
