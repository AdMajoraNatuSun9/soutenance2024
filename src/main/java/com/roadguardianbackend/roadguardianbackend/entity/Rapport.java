package com.roadguardianbackend.roadguardianbackend.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Rapport")
@AllArgsConstructor

public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private String titre;
    private String type;
    private String description;
    private String details;
//    private ControleRoutier controleRoutier;
    
}
