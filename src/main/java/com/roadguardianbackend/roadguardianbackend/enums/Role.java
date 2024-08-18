package com.roadguardianbackend.roadguardianbackend.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static com.roadguardianbackend.roadguardianbackend.constant.Authority.*;


@AllArgsConstructor
@NoArgsConstructor


public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_HR(HR_AUTHORITIES),
    ROLE_MANAGER(MANAGER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    public String[] getAuthorities() {
        return authorities;
    }
}
