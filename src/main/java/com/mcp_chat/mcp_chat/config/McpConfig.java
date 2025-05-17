package com.mcp_chat.mcp_chat.config;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
@AllArgsConstructor
public class McpConfig {

    private final ChatModel chatModel;
    private final List<McpSyncClient> mcpSyncClientList;

    private final String DEFAULT_SYSTEM_PROMPT = "답변은 한글로 남겨주세요.";


    @Bean
    public ChatClient prepareChatClient() {

        return ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM_PROMPT)
                .defaultTools(new SyncMcpToolCallbackProvider(mcpSyncClientList))
              // .defaultAdvisors(new MessageChatMemoryAdvisor())
                .build();
    }
}
