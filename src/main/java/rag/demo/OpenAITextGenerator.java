package rag.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Component
//@Bean
@Service

public class OpenAITextGenerator {

    @Value("${openrouter.api.url}")
    private String apiUrl;

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String generateText(String prompt) {
        try {

            JsonArray messages = new JsonArray();
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", prompt);
            messages.add(userMessage);

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "deepseek/deepseek-r1-distill-llama-70b:free");
            requestBody.add("messages", messages);

            // Create HTTP request
            System.out.println("Sending request to API: " + requestBody.toString());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/chat/completions")) // Append endpoint
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send request and get response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            // Parse the response
            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseJson.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating text: " + e.getMessage();
        }
    }
}
