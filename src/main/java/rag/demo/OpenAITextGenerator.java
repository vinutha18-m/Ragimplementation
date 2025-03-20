package rag.demo;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class OpenAITextGenerator {
    @Autowired
    private DeepSeekConfig deepSeekConfig;

    public String generateText(String prompt) {
        try {
            String requestBody = "{\"model\": \"deepseek-chat\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}], \"stream\": false}";
            // Create the JSON request body
            //JsonObject requestBody = new JsonObject();
            //requestBody.addProperty("prompt", prompt);
            //requestBody.addProperty("max_tokens", 100);
            //requestBody.addProperty("model", "deepseek-chat"); // Replace with the correct DeepSeek model name

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(deepSeekConfig.getTextApiUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + deepSeekConfig.getApiKey())
                    .POST(BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send the request and get the response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            // Parse the response
            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseJson.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating text: " + e.getMessage();
        }
    }
}