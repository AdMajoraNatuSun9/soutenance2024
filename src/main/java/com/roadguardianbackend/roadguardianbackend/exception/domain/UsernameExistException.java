package com.roadguardianbackend.roadguardianbackend.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UsernameExistException extends Exception {
    public UsernameExistException(String message) {
        super(message);
    }
}
