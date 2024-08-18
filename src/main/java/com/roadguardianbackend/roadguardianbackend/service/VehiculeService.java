 package com.roadguardianbackend.roadguardianbackend.service;

 import java.util.List;

 import com.roadguardianbackend.roadguardianbackend.entity.Vehicule;

 public interface VehiculeService {
     List<Vehicule> listVehicules();
     Vehicule addVehicule(Vehicule vehicule);
     Vehicule updatVehicule(Long id,Vehicule vehicule);
     void deleteVehicule(Long id);


    
 }
