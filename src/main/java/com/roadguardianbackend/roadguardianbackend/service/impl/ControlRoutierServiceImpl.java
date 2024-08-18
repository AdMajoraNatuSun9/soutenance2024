package com.roadguardianbackend.roadguardianbackend.service.impl;

import java.util.List;

import com.roadguardianbackend.roadguardianbackend.entity.ControleRoutier;
import com.roadguardianbackend.roadguardianbackend.repository.ControleRoutierRepository;
import com.roadguardianbackend.roadguardianbackend.service.ControleRoutierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ControlRoutierServiceImpl implements ControleRoutierService {

    @Autowired
    private ControleRoutierRepository controleRoutierRepository;

    @Override
    public List<ControleRoutier> listControleRoutier() {
       return controleRoutierRepository.findAll();
    }

    @Override
    public ControleRoutier addControleRoutier(ControleRoutier controleRoutier) {
       return controleRoutierRepository.save(controleRoutier);
    }

    @Override
    public ControleRoutier updateControleRoutier(Long id, ControleRoutier controleRoutier) {
        if(controleRoutierRepository.existsById(id)){
            controleRoutier.setId(id);
            return controleRoutierRepository.save(controleRoutier);
        }
        return null;
    }

    @Override
    public void deleteControleRoutier(Long id) {
       controleRoutierRepository.deleteById(id);
    }
    
}
