package rag.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("rag.demo")
@EnableConfigurationProperties({PdfProperties.class ,  DeepSeekConfig.class})

public class DemoApplication implements CommandLineRunner {
    @Autowired
    private PDFTextExtractor pdfTextExtractor; // Autowired instance of PDFTextExtractor

    @Autowired
    private PDFImageExtractor pdfImageExtractor; // Autowired instance of PDFImageExtractor

    @Autowired
    private OpenAITextGenerator openAITextGenerator; // Autowired instance of OpenAITextGenerator

    @Autowired
    private OpenAIImageGenerator openAIImageGenerator; // Autowired instance of OpenAIImageGenerator

    @Autowired
    private PdfProperties pdfProperties;
    // Directory to save extracted images

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        // Extract text and images
        String extractedText = pdfTextExtractor.extractText(pdfProperties.getFilePath());
        pdfImageExtractor.extractImages(pdfProperties.getFilePath(), pdfProperties.getOutputImageDir());
        // Instance method call

        // Generate related content using DeepSeek
        String generatedText = openAITextGenerator.generateText(extractedText);
        System.out.println("Generated Text: " + generatedText);

        // Generate related image using DeepSeek
        String generatedImageUrl = openAIImageGenerator.generateImage(extractedText);
        System.out.println("Generated Image URL: " + generatedImageUrl);
    }
}