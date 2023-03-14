package com.pluralsight.security;

/**
 *
 */
public enum ApplicationUserPermision {
    // TODO: does this crud idea makes sense?
    APP_CRUD("app:crud"), APP_READ("app:read"), APP_WRITE("app:write"), APP_UPDATE("app:update"), //
    USER_CRUD("user:crud"), USER_READ("user:read"), USER_CREATE("user:create"), USER_DELETE(
        "user:delete"), USER_UPDATE("user:update");

    private final String permission;

    public String getPermission() {
        return this.permission;
    }

    ApplicationUserPermision(String permission) {
        this.permission = permission;
    }

}
