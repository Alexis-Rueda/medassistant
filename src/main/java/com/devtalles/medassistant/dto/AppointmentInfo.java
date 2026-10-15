package com.devtalles.medassistant.dto;

public record AppointmentInfo(
        String doctorName,
        String specialty,
        String date,
        String time
) {
}