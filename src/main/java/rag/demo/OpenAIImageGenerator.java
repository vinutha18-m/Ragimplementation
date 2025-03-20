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

@Component
public class OpenAIImageGenerator {

    @Value("${openrouter.api.url}") // Use a single API URL
    private String apiUrl;

    @Value("${openrouter.api.key}")
    private String apiKey;

    /**
     * Generates an image using the OpenRouter API based on the given prompt.
     *
     * @param prompt The prompt to generate the image.
     * @return The URL of the generated image.
     */
    public String generateImage(String prompt) {
        try {
            // Create the JSON request body
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "deepseek/deepseek-r1-distill-llama-70b:free"); // Specify model
            requestBody.addProperty("prompt", prompt);
            requestBody.addProperty("n", 1);
            requestBody.addProperty("size", "1024x1024");

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/images/generations")) // Append correct endpoint
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send the request and get the response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            // Parse the response
            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseJson.getAsJsonArray("images")
                    .get(0).getAsJsonObject()
                    .get("url").getAsString(); // OpenRouter uses "images" not "data"
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating image: " + e.getMessage();
        }
    }
}

