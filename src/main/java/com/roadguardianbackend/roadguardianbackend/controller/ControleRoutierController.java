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

import com.roadguardianbackend.roadguardianbackend.entity.ControleRoutier;
import com.roadguardianbackend.roadguardianbackend.service.ControleRoutierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/controleRoutier")
@RequiredArgsConstructor
public class ControleRoutierController {
    @Autowired
    private ControleRoutierService controleRoutierService;

    @GetMapping
    public  List<ControleRoutier> listControleRoutier(){
        return controleRoutierService.listControleRoutier();
    }

    @PostMapping
    public ControleRoutier addControleRoutier( @RequestBody ControleRoutier controleRoutier){
        return controleRoutierService.addControleRoutier(controleRoutier);
    }

    @PutMapping("/{id}")
    public ControleRoutier updateControleRoutier(@PathVariable Long id,@RequestBody ControleRoutier controleRoutier){
        return controleRoutierService.updateControleRoutier(id, controleRoutier);

    }

    @DeleteMapping("/{id}")
    public void deleteControleRoutier(@PathVariable Long id){
        controleRoutierService.deleteControleRoutier(id);

    }
    
}
