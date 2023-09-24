package com.pluralsight.enums;
import com.pluralsight.security.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pluralsight.security.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    DOCTOR(
            Set.of(/*
                    DOCTOR_READ,
                    DOCTOR_UPDATE,
                    DOCTOR_DELETE,
                    DOCTOR_CREATE,
                    PATIENT_READ,
                    PATIENT_UPDATE,
                    PATIENT_DELETE,
                    PATIENT_CREATE*/
            )
    ),
    PATIENT(
            Set.of(
                   /* PATIENT_READ
                    PATIENT_UPDATE,
                    PATIENT_DELETE,
                    PATIENT_CREATE*/
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}