package com.roadguardianbackend.roadguardianbackend.dto;



import lombok.Data;

import java.util.Date;

@Data
public class ControleRoutierDto {

    private Long id;
    private Date date_created;
    private String lieu;
}
