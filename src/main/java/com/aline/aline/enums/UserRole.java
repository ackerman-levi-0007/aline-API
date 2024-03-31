package com.aline.aline.enums;

public enum UserRole {
    ROLE_LAB,
    ROLE_CLINIC,
    ROLE_DOCTOR,
    ROLE_ADMIN;

    public static boolean contains(String value) {
        for (UserRole day : values()) {
            if (day.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
