package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.DoctorInfo;

import java.util.List;

public interface DoctorService {
    List<DoctorInfo> searchDoctors(String query);
}