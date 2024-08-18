 package com.roadguardianbackend.roadguardianbackend.service.impl;

 import java.util.List;

 import com.roadguardianbackend.roadguardianbackend.entity.Vehicule;
 import com.roadguardianbackend.roadguardianbackend.repository.VehiculeRepository;
 import com.roadguardianbackend.roadguardianbackend.service.VehiculeService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 @Service
 public class VehiculeServiceImpl implements VehiculeService {

     @Autowired
     private VehiculeRepository vehiculeRepository;

     @Override
     public List<Vehicule> listVehicules() {
         return vehiculeRepository.findAll();
     }

     @Override
     public Vehicule addVehicule(Vehicule vehicule) {
         return vehiculeRepository.save(vehicule);
     }

     @Override
     public Vehicule updatVehicule(Long id, Vehicule vehicule) {
         if(vehiculeRepository.existsById(id)){
             vehicule.setId(id);
             return vehiculeRepository.save(vehicule);
         }
         return null;
     }

     @Override
     public void deleteVehicule(Long id) {
         vehiculeRepository.deleteById(id);
     }
    
 }
