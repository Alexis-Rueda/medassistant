package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.DrugInfo;

public interface DrugInfoService {
    DrugInfo getDrugInfo(String drugName);
}