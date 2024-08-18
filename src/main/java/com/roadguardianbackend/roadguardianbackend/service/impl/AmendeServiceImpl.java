package com.roadguardianbackend.roadguardianbackend.service.impl;

import java.util.List;

import com.roadguardianbackend.roadguardianbackend.entity.Amende;
import com.roadguardianbackend.roadguardianbackend.repository.AmendeRepository;
import com.roadguardianbackend.roadguardianbackend.service.AmendeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmendeServiceImpl implements AmendeService{
    @Autowired
    private AmendeRepository amendeRepository;

    @Override
    public List<Amende> listAmende() {
       return amendeRepository.findAll();
    }

    @Override
    public Amende addAmende(Amende amende) {
       return amendeRepository.save(amende);
    }

    @Override
    public Amende updateAmende(Long id, Amende amende) {
       if(amendeRepository.existsById(id)){
        amende.setId(id);
        return amendeRepository.save(amende);
       }
       return null;
    }

    @Override
    public void deleteAmende(Long id) {
       amendeRepository.deleteById(id);
    }
    
}
