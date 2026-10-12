package com.devtalles.medassistant.dto.analysis;

public record ConditionSummary(
        String conditionName,
        String description,
        String commonSymptoms,
        String severity,
        String whenToSeeDoctor
) {
}
