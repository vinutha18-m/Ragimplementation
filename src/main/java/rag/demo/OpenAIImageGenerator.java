package rag.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component // Mark this class as a Spring component
public class OpenAIImageGenerator {

    @Value("${deepseek.image.api.url}") // Inject the API URL from application.properties
    private String deepseekApiUrl;

    @Value("${deepseek.api.key}") // Inject the API key from application.properties
    private String deepseekApiKey;

    /**
     * Generates an image using the DeepSeek API based on the given prompt.
     *
     * @param prompt The prompt to generate the image.
     * @return The URL of the generated image.
     */
    public String generateImage(String prompt) {
        try {
            // Create the JSON request body
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("prompt", prompt);
            requestBody.addProperty("n", 1);
            requestBody.addProperty("size", "1024x1024");

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(deepseekApiUrl)) // Use injected API URL
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + deepseekApiKey) // Use injected API key
                    .POST(BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send the request and get the response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            // Parse the response
            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseJson.get("data").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating image: " + e.getMessage();
        }
    }
}
