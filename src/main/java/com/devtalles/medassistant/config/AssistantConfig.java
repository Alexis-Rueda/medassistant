package com.devtalles.medassistant.config;

import com.devtalles.medassistant.tools.AppointmentSearchTool;
import com.devtalles.medassistant.tools.DoctorInfoTool;
import com.devtalles.medassistant.tools.DrugInfoTool;
import com.devtalles.medassistant.tools.PatientInfoTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class AssistantConfig {

    @Value("classpath:prompts/system-prompt.st")
    private Resource systemPromptResource;

    private final AppointmentSearchTool appointmentSearchTool;
    private final DoctorInfoTool doctorInfoTool;
    private final PatientInfoTool patientInfoTool;
    private final DrugInfoTool drugInfoTool;

    @Bean("geminiClient")
    ChatClient geminiClient(GoogleGenAiChatModel chatModel) throws IOException {

        String systemPrompt = systemPromptResource.getContentAsString(StandardCharsets.UTF_8)
                .replace("{currentDate}", LocalDate.now().toString());

        return ChatClient.builder(chatModel)
                .defaultSystem(systemPrompt)
                .defaultTools(appointmentSearchTool, doctorInfoTool, patientInfoTool, drugInfoTool)
                .build();
    }

    @Bean("ollamaClient")
    ChatClient ollamaClient(OllamaChatModel chatModel) throws IOException {
        String systemPrompt = systemPromptResource.getContentAsString(StandardCharsets.UTF_8)
                .replace("{currentDate}", LocalDate.now().toString());
        return ChatClient.builder(chatModel)
                .defaultSystem(systemPrompt)
                .defaultTools(appointmentSearchTool, doctorInfoTool, patientInfoTool, drugInfoTool)
                .build();
    }
}