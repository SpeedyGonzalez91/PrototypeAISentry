package com.example.aisentrytest.Service;

import com.example.aisentrytest.Building;
import com.example.aisentrytest.Floor;
import com.example.aisentrytest.Place;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode messagesArray = objectMapper.createArrayNode();

            // Create the user message
            ObjectNode userMessage = objectMapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", userQuery);

            // Add the user message to the messages array
            messagesArray.add(userMessage);

            // Get the building data from the JSON file
            List<Building> buildings = getBuildingData();

            for (Building building : buildings) {
                if (userQuery.contains(building.getName())) {
                    // Handle the case when the user's query is related to the building
                    // For example, you can use building.getDirections(), building.getAmenities(), etc., to construct the AI response

                    // First, add the user message to the messages array
                    messagesArray.add(userMessage);

                    // Then, add the building information as a separate assistant message
                    ObjectNode buildingMessage = objectMapper.createObjectNode();
                    buildingMessage.put("role", "assistant");
                    buildingMessage.put("content", "Building Name: " + building.getName() + ", Description: " + building.getDescription());

                    for (Floor floor : building.getFloors()) {
                        buildingMessage.put("content", buildingMessage.get("content").asText() + ", Floor Number: " + floor.getFloorNumber());

                        for (Place place : floor.getPlaces()) {
                            buildingMessage.put("content", buildingMessage.get("content").asText() +
                                    ", Place Name: " + place.getName() +
                                    ", Place Description: " + place.getDescription() +
                                    ", Directions: " + place.getDirections() +
                                    ", Category: " + place.getCategory());
                        }
                    }
                    messagesArray.add(buildingMessage);
                }
            }

            // ... (other AI-related logic, if any)

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

            // Close the HttpClient (only if you don't need it for other requests)
            httpClient.close();

            return extractAIResponseContent(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: Failed to get response from the AI service.");
        }
    }

    private List<Building> getBuildingData() {
        // Implement this method to read and parse the JSON file containing building data
        // You can use any JSON parsing library or manually parse the JSON data to create a list of Building objects.
        // For simplicity, I'll provide a sample implementation using Jackson ObjectMapper:

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Assuming the JSON file is named "buildings.json" and located in the resources/data/ directory.
            File file = new ClassPathResource("data/buildings.json").getFile();
            TypeReference<List<Building>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: Failed to read building data from JSON file.");
        }
    }

    private String extractAIResponseContent(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Parse the JSON response body to extract AI response content
        ObjectNode responseNode = objectMapper.readValue(responseBody, ObjectNode.class);
        ArrayNode choicesArray = responseNode.get("choices").isArray() ? (ArrayNode) responseNode.get("choices") : null;
        if (choicesArray != null && !choicesArray.isEmpty()) {
            return choicesArray.get(0).get("message").get("content").asText();
        } else {
            throw new RuntimeException("Error: AI response is missing or empty.");
        }
    }

}

