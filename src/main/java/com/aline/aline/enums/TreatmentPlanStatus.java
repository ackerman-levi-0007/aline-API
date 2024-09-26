package com.aline.aline.enums;

public enum TreatmentPlanStatus {
    draft,
    shared,
    modificationRequested,
    confirmed,
    rejected,
    history;

    public static TreatmentPlanStatus fromString(String value) {
        try {
            return TreatmentPlanStatus.valueOf(value.toUpperCase());  // Convert the string to enum if it matches
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;  // Return null if no matching enum is found
        }
    }
}
