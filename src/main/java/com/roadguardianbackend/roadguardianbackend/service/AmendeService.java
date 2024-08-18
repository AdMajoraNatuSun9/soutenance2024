package com.roadguardianbackend.roadguardianbackend.service;

import java.util.List;

import com.roadguardianbackend.roadguardianbackend.entity.Amende;

public interface AmendeService {
    List<Amende> listAmende();
    Amende addAmende(Amende amende);

    void deleteAmende(Long id);

    Amende updateAmende(Long id, Amende amende);
}
