package com.devtalles.medassistant.agent;

public interface AppointmentChainService {
    String bookAppointmentChain(String userRequest, String model, Long userId);
}