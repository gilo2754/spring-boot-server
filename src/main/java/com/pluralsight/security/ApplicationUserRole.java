package com.pluralsight.security;

import static com.pluralsight.security.ApplicationUserPermision.APP_READ;
import static com.pluralsight.security.ApplicationUserPermision.APP_UPDATE;
import static com.pluralsight.security.ApplicationUserPermision.APP_WRITE;
import static com.pluralsight.security.ApplicationUserPermision.USER_READ;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

/**
 *
 */
public enum ApplicationUserRole {
    // Admin, well all rights
    ADMIN(Sets.newHashSet()),
    // Student will be the standard user
    USER(Sets.newHashSet(APP_READ, APP_WRITE, APP_UPDATE)),
    // External are users, wich are not registers or with a uncompleted profile
    EXTERNAL(Sets.newHashSet(APP_READ, USER_READ));

    private final Set<ApplicationUserPermision> permissions;

    ApplicationUserRole(Set<ApplicationUserPermision> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermision> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()//
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return permissions;
    }
}
