package rag.demo;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekConfig {
    private String apiKey; // Matches deepseek.apiKey in application.properties
    private String textApiUrl; // Matches deepseek.textApiUrl
    private String imageApiUrl; 

    // Getters and Setters
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTextApiUrl() {
        return textApiUrl;
    }

    public void setTextApiUrl(String textApiUrl) {
        this.textApiUrl = textApiUrl;
    }

    public String getImageApiUrl() {
        return imageApiUrl;
    }

    public void setImageApiUrl(String imageApiUrl) {
        this.imageApiUrl = imageApiUrl;
    }
}