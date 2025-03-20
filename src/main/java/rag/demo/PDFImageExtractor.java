package rag.demo;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

@Component // Mark this class as a Spring component
public class PDFImageExtractor {

    /**
     * Extracts images from a PDF file and saves them to the specified output directory.
     *
     * @param filePath  Path to the PDF file.
     * @param outputDir Directory to save the extracted images.
     * @throws IOException If an error occurs while processing the PDF.
     */
    public void extractImages(String filePath, String outputDir) throws IOException {
        // Load the PDF document
        PDDocument document = PDDocument.load(new File(filePath));

        int pageNum = 0;
        for (PDPage page : document.getDocumentCatalog().getPages()) {
            PDResources resources = page.getResources();
            int imageNum = 0;

            // Iterate through all XObjects (images) in the page
            for (COSName name : resources.getXObjectNames()) {
                PDXObject xobject = resources.getXObject(name);

                // Check if the XObject is an image
                if (xobject instanceof PDImageXObject) {
                    PDImageXObject image = (PDImageXObject) xobject;

                    // Save the image to the output directory
                    String filename = outputDir + "/image_" + pageNum + "_" + imageNum + ".png";
                    ImageIO.write(image.getImage(), "PNG", new File(filename));
                    imageNum++;
                }
            }
            pageNum++;
        }

        // Close the PDF document
        document.close();
    }
}