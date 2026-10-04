package com.devtalles.medassistant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AssistantServiceImpl implements AssistantService {

    private final ChatClient geminiClient;
    private final ChatClient ollamaClient;

    public AssistantServiceImpl(
            @Qualifier("geminiClient") ChatClient geminiClient,
            @Qualifier("ollamaClient") ChatClient ollamaClient) {
        this.geminiClient = geminiClient;
        this.ollamaClient = ollamaClient;
    }

    @Override
    public String chat(String prompt, String model) {
        log.info("Chat request - modelo: {} ", model);

        return resolveClient(model)
                .prompt(prompt).call().content();
    }

    @Override
    public Flux<String> chatStream(String prompt, String model) {
        log.info("Stream request - modelo: {} ", model);

        return resolveClient(model)
                .prompt(prompt).stream().content();
    }

    @Override
    public String explainCondition(String condition, String model) {
        log.info("Explain request — condición: {}, modelo: {}", condition, model);

        String message = "Explicame qué es {condicion} en términos simples para un paciente.\n" +
                "Usá lenguaje accesible y evitá jerga médica innecesaria.";

        return resolveClient(model)
                .prompt(message)
                .call()
                .content();
    }

    private ChatClient resolveClient(String model){
        return "ollama".equalsIgnoreCase(model) ? ollamaClient : geminiClient;
    }
}
