package com.pietro.codesentinel.aicontroller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisService {
    private final ChatClient chatClient;

    public DiagnosisService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String diagnose(String error){
        return chatClient.prompt()
                .system("You are an assistant for diagnosing Java application errors. Analyze the provided exception and explain, " +
                        "concisely and in Portuguese, the probable cause and what the developer should investigate to fix it.")
                .user(error)
                .call()
                .content();
    }
}
