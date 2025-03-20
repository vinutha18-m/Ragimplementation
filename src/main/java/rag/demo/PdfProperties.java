package rag.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pdf")
public class PdfProperties {

    private String filePath;
    private String outputImageDir;
    

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOutputImageDir() {
        return outputImageDir;
    }

    public void setOutputImageDir(String outputImageDir) {
        this.outputImageDir = outputImageDir;
    }
}
