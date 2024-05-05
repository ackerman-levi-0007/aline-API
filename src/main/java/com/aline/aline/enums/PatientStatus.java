package com.aline.aline.enums;

public enum PatientStatus {
    scanned,
    planShared,
    confirmed,
    rejected,
    modificationRequested,
    treatmentStarted,
    treatmentComplete;

    public static boolean contains(String value) {
        for (PatientStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
