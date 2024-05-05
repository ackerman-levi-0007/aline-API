package com.aline.aline.enums;

public enum UserRole {
    ROLE_LAB,
    ROLE_CLINIC,
    ROLE_DOCTOR,
    ROLE_ADMIN;

    public static boolean contains(String value) {
        for (UserRole role : values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
