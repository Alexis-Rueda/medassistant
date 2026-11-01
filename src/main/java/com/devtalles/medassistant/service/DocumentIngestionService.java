package com.devtalles.medassistant.service;

import org.springframework.core.io.Resource;

public interface DocumentIngestionService {

    String ingestNewDocument(Resource resource);
}