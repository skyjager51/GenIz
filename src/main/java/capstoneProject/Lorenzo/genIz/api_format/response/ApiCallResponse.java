package capstoneProject.Lorenzo.genIz.api_format.response;

import java.util.List;

import capstoneProject.Lorenzo.genIz.api_format.response.map_model_response.Choices;
import capstoneProject.Lorenzo.genIz.api_format.response.map_model_response.TotalTokens;

public class ApiCallResponse {
    private List<Choices> choices;
    private TotalTokens usage;

    public List<Choices> getChoices() {
        return choices;
    }
    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }
    public TotalTokens getUsage() {
        return usage;
    }
    public void setUsage(TotalTokens usage) {
        this.usage = usage;
    }
}
