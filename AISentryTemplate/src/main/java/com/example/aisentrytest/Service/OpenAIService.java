package com.example.aisentrytest.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.springframework.stereotype.Service;




@Service
public class OpenAIService {

    public String getResponse(String userQuery) {
        // Create an HttpClient instance
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create an HTTP POST request
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        HttpPost httpPost = new HttpPost(apiUrl);
        String apiKey = "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";
        httpPost.setHeader("Authorization", "Bearer " + apiKey);
        httpPost.setHeader("Content-Type", "application/json");

        try {
            // Create JSON request using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode messagesArray = objectMapper.createArrayNode();

            // Create the user message
            ObjectNode userMessage = objectMapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", userQuery);

            // Add the user message to the messages array
            messagesArray.add(userMessage);


            // Create the system message (optional)
            ObjectNode systemMessage = objectMapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant.");

            // Add the system message to the messages array
            messagesArray.add(systemMessage);

            // Create the JSON request body with 'messages' property
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.set("messages", messagesArray);
            requestBody.put("model", "gpt-3.5-turbo");

            String requestBodyString = objectMapper.writeValueAsString(requestBody);
            httpPost.setEntity(new StringEntity(requestBodyString, ContentType.APPLICATION_JSON));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Get the response body
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Close the HttpClient
            httpClient.close();

            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: Failed to get response from the AI service.");
        }
    }
}








