package com.roadguardianbackend.roadguardianbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadguardianbackend.roadguardianbackend.entity.ControleRoutier;
import com.roadguardianbackend.roadguardianbackend.entity.Rapport;
import com.roadguardianbackend.roadguardianbackend.service.RapportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rapport")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping
    public List<Rapport> listRapport(){
        return rapportService.listRapport();
    }

    @GetMapping("/{id}")
    public  Optional<Rapport>  findRapportById(@PathVariable Long id){
        return rapportService.findRapportById(id);

    }

    @PostMapping
    public  Rapport addRapport(@RequestBody Rapport rapport){
        return rapportService.addRapport(rapport);
    }

    @PutMapping("/{id}")
    public Rapport updateRapport(@PathVariable Long id , @RequestBody Rapport rapport){
        return rapportService.updateRapport(id, rapport);

    }

    @DeleteMapping("/{id}")
    public  void deleteRapport(@PathVariable Long id){
         rapportService.deleteRapport(id);

    }


    
}
