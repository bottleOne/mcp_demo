package com.mcp_chat.mcp_chat;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpTools {

    //@Tool(description = "숫자는 몇인가요?")
    public int getNumber(){
        return 10;
    }
}
