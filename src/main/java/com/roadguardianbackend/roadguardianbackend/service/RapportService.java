package com.roadguardianbackend.roadguardianbackend.service;

import java.util.List;
import java.util.Optional;

import com.roadguardianbackend.roadguardianbackend.entity.Rapport;

public interface RapportService {
    
    List<Rapport> listRapport();
    Optional<Rapport>  findRapportById(Long id);
    Rapport addRapport(Rapport rapport);
    Rapport updateRapport(Long id,Rapport rapport);
    void deleteRapport(Long id);
    
}
