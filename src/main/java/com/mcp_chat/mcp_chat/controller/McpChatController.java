package com.mcp_chat.mcp_chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class McpChatController {

    private final OllamaChatModel chatModel;

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/ai/generate/custom")
    public String generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        PromptTemplate template = new PromptTemplate("안녕하세요 저의 이름은 {name}입니다. 직업은 {job}이구.");
        Prompt prompt = template.create(Map.of("name", "전병일", "job", "개발자"));
        Generation result = this.chatModel.call(prompt).getResult();

        return result.getOutput().toString();
    }
}
