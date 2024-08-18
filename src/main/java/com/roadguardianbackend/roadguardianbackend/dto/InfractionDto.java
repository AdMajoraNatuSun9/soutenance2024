package com.roadguardianbackend.roadguardianbackend.dto;


import lombok.Data;

@Data
public class InfractionDto {
    private Long id;
    private String type_infraction;
    private String description;
}
