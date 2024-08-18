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

import com.roadguardianbackend.roadguardianbackend.entity.Amende;
import com.roadguardianbackend.roadguardianbackend.service.AmendeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/amende")
@RequiredArgsConstructor
public class AmendeController {
    @Autowired
    private AmendeService amendeService;

    @GetMapping("/list")
    public List<Amende> listAmende(){
        return amendeService.listAmende();
    } 


    @PostMapping("/add")
    public  Amende addAmende(@RequestBody Amende amende){
        return amendeService.addAmende(amende);
    }

   @PutMapping("update/{id}")
   public Amende updateAmende(@PathVariable Long id, @RequestBody Amende amende){
    return amendeService.updateAmende(id, amende);

   }

   @DeleteMapping("/delete/{id}")
   public  void deleteAmende(@PathVariable Long id){
    amendeService.deleteAmende(id);

   }

    
}
