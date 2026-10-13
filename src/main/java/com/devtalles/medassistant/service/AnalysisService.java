package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.analysis.ConditionSummary;
import com.devtalles.medassistant.dto.analysis.QueryClassification;
import com.devtalles.medassistant.dto.analysis.SymptomAnalysis;

import java.util.List;

public interface AnalysisService {
    ConditionSummary summarizeCondition(String condition, String model);
    List<ConditionSummary> listRelatedConditions(String symptoms, String model);
    SymptomAnalysis analyzeSymptoms(String symptoms, String model);
    QueryClassification classifyQuery(String query, String model);
}