package com.devtalles.medassistant.dto;

public record IngestionResult(
        String filename,
        int fragmentsCount
) {
}