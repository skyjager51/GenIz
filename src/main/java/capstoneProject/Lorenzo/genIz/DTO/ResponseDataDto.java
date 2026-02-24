package capstoneProject.Lorenzo.genIz.DTO;

public class ResponseDataDto {
    private String llmOutput;
    private String tokenCount;
    
    public ResponseDataDto(String llmOutput, String tokenCount) {
        this.llmOutput = llmOutput;
        this.tokenCount = tokenCount;
    }

    public String getLlmOutput() {
        return llmOutput;
    }

    public void setLlmOutput(String llmOutput) {
        this.llmOutput = llmOutput;
    }

    public String getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(String tokenCount) {
        this.tokenCount = tokenCount;
    }

}
