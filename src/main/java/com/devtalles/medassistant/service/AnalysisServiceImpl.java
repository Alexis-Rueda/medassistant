package com.devtalles.medassistant.service;

import com.devtalles.medassistant.config.ClientResolver;
import com.devtalles.medassistant.dto.analysis.ConditionSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
