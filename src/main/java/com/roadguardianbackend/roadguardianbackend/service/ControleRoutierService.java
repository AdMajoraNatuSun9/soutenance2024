package com.roadguardianbackend.roadguardianbackend.service;

import java.util.List;

import com.roadguardianbackend.roadguardianbackend.entity.ControleRoutier;

public interface ControleRoutierService {
    List<ControleRoutier> listControleRoutier();
    ControleRoutier addControleRoutier(ControleRoutier controleRoutier);
    ControleRoutier updateControleRoutier(Long id,ControleRoutier controleRoutier);
    void deleteControleRoutier(Long id);
}
