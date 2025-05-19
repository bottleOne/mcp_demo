package com.mcp_chat.mcp_chat.config;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class McpConfig {
    private final List<McpSyncClient> mcpSyncClientList;

    private final String DEFAULT_SYSTEM_PROMPT = "현재를 기준으로 한글로 대답해줘";

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {

        SyncMcpToolCallbackProvider syncMcpToolCallbackProvider = new SyncMcpToolCallbackProvider(mcpSyncClientList);

        return ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM_PROMPT) // 전반적인 규약 설정
                .defaultToolCallbacks(syncMcpToolCallbackProvider.getToolCallbacks()) // mcp tool 불러온다
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
