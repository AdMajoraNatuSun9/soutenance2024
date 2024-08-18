package com.roadguardianbackend.roadguardianbackend.service.impl;

import java.util.List;
import java.util.Optional;

import com.roadguardianbackend.roadguardianbackend.entity.Rapport;
import com.roadguardianbackend.roadguardianbackend.repository.RapportRepository;
import com.roadguardianbackend.roadguardianbackend.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RapportServiceImpl  implements RapportService{

    @Autowired
    private RapportRepository rapportRepository;

    @Override
    public List<Rapport> listRapport() {
        return rapportRepository.findAll();
    }

    @Override
    public Optional<Rapport> findRapportById(Long id) {
        return rapportRepository.findById(id);
    }

    @Override
    public Rapport addRapport(Rapport rapport) {
        return rapportRepository.save(rapport);
    }

    @Override
    public Rapport updateRapport(Long id, Rapport rapport) {
        if(rapportRepository.existsById(id)){
            rapport.setId(id);
            return rapportRepository.save(rapport);
        }
        return null;
        
    }

    @Override
    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }
    
}
