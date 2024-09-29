package com.aline.aline.services;

import org.springframework.stereotype.Service;

@Service
public interface IRebootService {
    void createReboot(String patientID);
}
