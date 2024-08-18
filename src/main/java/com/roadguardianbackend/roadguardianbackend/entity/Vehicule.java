package com.roadguardianbackend.roadguardianbackend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehicule")
@Data
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modele;
    private String plaque_immatriculation;
}
