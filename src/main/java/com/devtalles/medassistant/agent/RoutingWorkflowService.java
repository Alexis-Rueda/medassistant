package com.devtalles.medassistant.agent;

public interface RoutingWorkflowService {
    String routeQuery(String query, String model, Long userId);
}