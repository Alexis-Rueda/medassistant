package com.devtalles.medassistant.agent;

import com.devtalles.medassistant.config.ClientResolver;
import com.devtalles.medassistant.dto.AppointmentInfo;
import com.devtalles.medassistant.dto.agent.AppointmentRequest;
import com.devtalles.medassistant.service.AppointmentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentChainServiceImpl implements AppointmentChainService{

    private final ClientResolver clientResolver;
    private final AppointmentService appointmentService;

    @Value("classpath:prompts/appointment-extraction.st")
    private Resource extractionResource;

    @Value("classpath:prompts/appointment-confirmation.st")
    private Resource confirmationResource;

    private PromptTemplate extractionTemplate;
    private PromptTemplate confirmationTemplate;

    @PostConstruct
    void init(){
        extractionTemplate = new PromptTemplate(extractionResource);
        confirmationTemplate = new PromptTemplate(confirmationResource);
    }

    @Override
    public String bookAppointmentChain(String userRequest, String model, Long userId) {
        AppointmentRequest extracted = interpret(userRequest, model);
        List<AppointmentInfo> available = search(extracted);

        return confirm(userRequest, available, model);
    }

    private AppointmentRequest interpret(String userRequest, String model){

        String prompt = extractionTemplate.render(Map.of("pedido", userRequest));

        AppointmentRequest extracted = clientResolver.resolve(model)
                .prompt()
                .user(prompt)
                .call()
                .entity(AppointmentRequest.class);

        log.info("Chain paso 1 — extraído: specialty={}, date={}", extracted.specialty(), extracted.date());
        return extracted;
    }

    private List<AppointmentInfo> search(AppointmentRequest extracted){

        List<AppointmentInfo> available = appointmentService.findAvailableAppointments(
                extracted.specialty(), LocalDate.parse(extracted.date())
        );

        log.info("Chain paso 2 — turnos encontrados: {}", available.size());
        return available;
    }

    private String confirm(String userRequest, List<AppointmentInfo> available, String model){

        String prompt = confirmationTemplate.render(Map.of(
                "pedido", userRequest,
                "turnos", available.isEmpty() ? "No hay turnos disponibles" : available.toString()
        ));

        log.info("Chain paso 3 — turnos enviados al modelo: {}", available);

        return clientResolver.resolve(model)
                .prompt()
                .user(prompt)
                .call()
                .content();

    }




}