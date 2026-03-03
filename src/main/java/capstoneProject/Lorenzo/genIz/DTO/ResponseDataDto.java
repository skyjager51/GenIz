package capstoneProject.Lorenzo.genIz.DTO;

public class ResponseDataDto {
    private String llmOutput;
    private Integer tokenCount;
    
    public ResponseDataDto(String llmOutput, Integer tokenCount) {
        this.llmOutput = llmOutput;
        this.tokenCount = tokenCount;
    }

    public String getLlmOutput() {
        return llmOutput;
    }

    public void setLlmOutput(String llmOutput) {
        this.llmOutput = llmOutput;
    }

    public Integer getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }

}
