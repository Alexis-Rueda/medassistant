package com.devtalles.medassistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClientResolver {

    private final ChatClient geminiClient;
    private final ChatClient ollamaClient;
    private final ChatModel geminiModel;
    private final ChatModel ollamaModel;
    private final VectorStore googleVectorStore;
    private final VectorStore ollamaVectorStore;

    public ClientResolver(
            @Qualifier("geminiClient") ChatClient geminiClient,
            @Qualifier("ollamaClient") ChatClient ollamaClient,
            @Qualifier("googleGenAiChatModel") ChatModel geminiModel,
            @Qualifier("ollamaChatModel") ChatModel ollamaModel,
            @Qualifier("googleVectorStore") VectorStore googleVectorStore,
            @Qualifier("ollamaVectorStore") VectorStore ollamaVectorStore

    ) {
        this.googleVectorStore = googleVectorStore;
        this.ollamaVectorStore = ollamaVectorStore;
        this.geminiModel = geminiModel;
        this.ollamaModel = ollamaModel;
        this.geminiClient = geminiClient;
        this.ollamaClient = ollamaClient;
    }

    public ChatClient resolve(String model){
        return switch (normalize(model)){
            case "gemini" -> geminiClient;
            case "ollama" -> ollamaClient;
            default -> throw new IllegalArgumentException(
                    "Modelo no soportado: '%s'. Valores válidos: gemini, ollama".formatted(model));
        };
    }

    public ChatModel resolveModel(String model) {
        return switch (normalize(model)) {
            case "gemini" -> geminiModel;
            case "ollama" -> ollamaModel;
            default -> throw new IllegalArgumentException(
                    "Modelo no soportado: '%s'. Valores válidos: gemini, ollama".formatted(model));
        };
    }

    public VectorStore resolveVectorStore(String model) {
        return switch (normalize(model)) {
            case "gemini" -> googleVectorStore;
            case "ollama" -> ollamaVectorStore;
            default -> throw new IllegalArgumentException(
                    "Modelo no soportado: '%s'. Valores válidos: gemini, ollama".formatted(model));
        };
    }

    private String normalize(String model){
        return model == null ? "gimini" : model.strip().toLowerCase();
    }
}