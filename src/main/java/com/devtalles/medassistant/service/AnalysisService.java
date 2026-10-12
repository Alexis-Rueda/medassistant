package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.analysis.ConditionSummary;

public interface AnalysisService {
    ConditionSummary summarizeCondition(String condition, String model);
}
