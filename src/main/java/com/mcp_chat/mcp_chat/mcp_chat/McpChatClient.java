package com.mcp_chat.mcp_chat.mcp_chat;

import com.mcp_chat.mcp_chat.config.McpConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class McpChatClient {

    private final ChatModel chatModel;
    private final McpConfig mcpConfig;


    public ChatClient getClient(){
        return mcpConfig.chatClient(chatModel);
    }
}
