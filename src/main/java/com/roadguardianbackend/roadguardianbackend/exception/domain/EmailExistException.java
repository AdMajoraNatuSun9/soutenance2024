package com.roadguardianbackend.roadguardianbackend.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
