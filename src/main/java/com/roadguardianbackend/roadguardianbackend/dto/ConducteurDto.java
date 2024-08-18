package com.roadguardianbackend.roadguardianbackend.dto;

import lombok.Data;

@Data
public class ConducteurDto {

    private Long id;
    private String nom;
    private String permit_conduire;
}
