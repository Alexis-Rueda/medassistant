package com.devtalles.medassistant.controller;

import com.devtalles.medassistant.dto.ChatRequest;
import com.devtalles.medassistant.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AssistantService assistantService;

    @PostMapping
    public ResponseEntity<String> chat(
            @RequestBody ChatRequest request
    ){
        return ResponseEntity.ok(assistantService.chat(request.prompt(), request.model()));
    }

    @PostMapping(value = "/stream", produces = "text/event-stream; charset=UTF-8")
    public Flux<String> chatStream(
            @RequestBody ChatRequest request
    ){
        return assistantService.chatStream(request.prompt(), request.model());
    }

}