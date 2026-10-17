package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.PatientInfo;

public interface PatientService {
    PatientInfo getPatientInfo(Long patientId);
}