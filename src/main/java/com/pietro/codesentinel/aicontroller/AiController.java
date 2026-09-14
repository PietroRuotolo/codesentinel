package com.pietro.codesentinel.aicontroller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
public class AiController {
    private final ChatClient chatClient;
    private final DiagnosisService diagnosisService;

    public AiController(ChatClient.Builder builder, DiagnosisService diagnosisService) {

        this.chatClient = builder.build();
        this.diagnosisService = diagnosisService;
    }

    @GetMapping("/ai/test")
    public String test(@RequestParam String question){
        return chatClient.prompt().user(question).call().content();
    }

    @PostMapping("/diagnose")
    public String diagnose(@RequestBody String error){
        return diagnosisService.diagnose(error);
    }

}
