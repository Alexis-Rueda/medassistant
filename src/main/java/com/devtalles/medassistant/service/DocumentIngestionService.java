package com.devtalles.medassistant.service;

import com.devtalles.medassistant.dto.IngestionResult;
import org.springframework.core.io.Resource;

public interface DocumentIngestionService {

    IngestionResult ingestNewDocument(Resource resource);
}