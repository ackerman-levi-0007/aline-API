package com.aline.aline.payload.PatientDentalDetailsMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reboot {
    private int totalReboots;
    private int latestReboot;
}
