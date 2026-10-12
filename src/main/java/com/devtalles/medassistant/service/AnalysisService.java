package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.analysis.ConditionSummary;
import com.devtalles.medassistant.dto.analysis.SymptomAnalysis;

import java.util.List;

public interface AnalysisService {
    ConditionSummary summarizeCondition(String condition, String model);
    List<ConditionSummary> listRelatedConditions(String symptoms, String model);
    SymptomAnalysis analyzeSymptoms(String symptoms, String model);
}
