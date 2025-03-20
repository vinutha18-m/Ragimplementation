package rag.demo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component // Register as a Spring Bean
public class PDFTextExtractor {

    /**
     * Extracts text from a PDF file.
     * @param filePath Path to the PDF file.
     * @return Extracted text as a String.
     * @throws IOException If an error occurs while reading the PDF.
     */
    public String extractText(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }
}

