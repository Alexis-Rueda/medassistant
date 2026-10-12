package com.devtalles.medassistant.service;

import com.devtalles.medassistant.config.ClientResolver;
import com.devtalles.medassistant.dto.analysis.ConditionSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService{
    private final ClientResolver clientResolver;

    @Override
    public ConditionSummary summarizeCondition(String condition, String model) {

        log.info("Análisis estructurado de condición: {}, modelo: {}", condition, model);

        return clientResolver.resolve(model)
                .prompt()
                .user("Proporcioná un resumen médico educativo sobre: " + condition)
                .call()
                .entity(ConditionSummary.class);

//        var converter = new BeanOutputConverter<>(ConditionSummary.class);
//
//        String format = converter.getFormat();
//        log.info("Instrucciones de formato generadas:\n{}", format);
//
//        String prompt = """
//                Proporcioná un resumen médico educativo sobre: %s
//
//                %s
//                """.formatted(condition, format);
//
//        String jsonResponse = resolveClient(model)
//                .prompt(prompt)
//                .call()
//                .content();
//        log.info("Respuesta JSON del modelo:\n{}", jsonResponse);
//
//        return converter.convert(jsonResponse);
    }

    @Override
    public List<ConditionSummary> listRelatedConditions(String symptoms, String model) {
        log.info("Listado de condiciones realacionadas - modelo: {} ", model);
        return clientResolver.resolve(model)
                .prompt()
                .user("Listá las 3 condiciones médicas más probables " +
                        "para estos síntomas: " + symptoms
                )
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }

}
