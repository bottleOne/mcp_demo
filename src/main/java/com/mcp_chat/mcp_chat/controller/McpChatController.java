package com.mcp_chat.mcp_chat.controller;

import com.mcp_chat.mcp_chat.mcp_chat.McpChatClient;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
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

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class McpChatController {

    private final McpChatClient chatModel;

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message") @NotNull String message) {
        ChatClient chatClient = chatModel.getClient();

        return Map.of("generation", chatClient
                .prompt(message)
                .call()
                .chatResponse()
                .getResult()
                .toString());
    }

    @GetMapping("/ai/generate/custom")
    public Map<String, Object> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        //1.기본 메시지 처라
        /*String format = "안녕하세요 저의 이름은 {name}입니다.";
        PromptTemplate template = new PromptTemplate(Message);
         Prompt prompt = template.create(Map.of("name", message));
        */

        //2. Message객체를 이용한 더욱 유연한 응답처리
        SystemMessage systemMessage = new SystemMessage("이름이과 직업에 관련 없는 요청이 들어오면 '이름을 말씀해주세요 라고 알려줘'");
        UserMessage userMessage = new UserMessage(String.format("안녕하세요 저의 이름은 {name}입니다.", message));
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));


        Map<String, Object> context = this.chatModel.getClient().prompt(prompt).call().chatClientResponse().context();

        return context;
    }
}
