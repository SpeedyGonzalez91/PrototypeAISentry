package com.example.aisentrytest.Controller;

import com.example.aisentrytest.Service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VirtualAssistantController {

        private final OpenAIService openAIService;

        public VirtualAssistantController(OpenAIService openAIService) {
            this.openAIService = openAIService;
        }


        @PostMapping("/query")
        public String queryAssistant(@RequestBody String query) {

            return openAIService.getResponse(query);
        }
    }


