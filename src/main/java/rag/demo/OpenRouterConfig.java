package rag.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openrouter") // Matches application.properties keys
public class OpenRouterConfig {
    private String apiKey; // Matches openrouter.apiKey in properties
    private String apiUrl; // Matches openrouter.apiUrl

    // Getters and Setters
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
