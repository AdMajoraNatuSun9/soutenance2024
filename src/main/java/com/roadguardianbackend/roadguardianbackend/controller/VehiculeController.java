 package com.roadguardianbackend.roadguardianbackend.controller;

 import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.PutMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import com.roadguardianbackend.roadguardianbackend.entity.Rapport;
 import com.roadguardianbackend.roadguardianbackend.entity.Vehicule;
 import com.roadguardianbackend.roadguardianbackend.service.VehiculeService;

 import lombok.AllArgsConstructor;
 import lombok.RequiredArgsConstructor;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/vehicule")
 public class VehiculeController {
     @Autowired
     private VehiculeService vehiculeService;

     @GetMapping("/list")
     public  List<Vehicule> listVehicules(){
         return vehiculeService.listVehicules();
     }

     @PostMapping("/add")
     public Vehicule addVehicule(@RequestBody Vehicule vehicule){
         return vehiculeService.addVehicule(vehicule);
     }

     @PostMapping("update/{id}")
     public Vehicule updatVehicule(@PathVariable Long id,@RequestBody Vehicule vehicule){
         return vehiculeService.updatVehicule(id, vehicule);
     }

     @DeleteMapping("delete/{id}")
     public void deleteVehicule(@PathVariable Long id){
          vehiculeService.deleteVehicule(id);
     }
 }
