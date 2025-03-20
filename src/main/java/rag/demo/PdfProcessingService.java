package rag.demo;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class PdfProcessingService {

    private final PdfProperties pdfProperties;

    
    public PdfProcessingService(PdfProperties pdfProperties) {
        this.pdfProperties = pdfProperties;
    }

    public void processPdf() {
        String pdfPath = pdfProperties.getFilePath();
        String outputDir = pdfProperties.getOutputImageDir();

        if (pdfPath == null || outputDir == null) {
            System.err.println("Error: PDF file path or output directory is not set in properties.");
            return;
        }

        System.out.println("Processing PDF: " + pdfPath);
        System.out.println("Saving images to: " + outputDir);

        // Ensure the output directory exists
        File dir = new File(outputDir);
        if (!dir.exists() && dir.mkdirs()) {
            System.out.println("Created output directory: " + outputDir);
        } else if (!dir.exists()) {
            System.err.println("Error: Failed to create output directory.");
            return;
        }

        // Add your PDF processing logic here
    }
}

