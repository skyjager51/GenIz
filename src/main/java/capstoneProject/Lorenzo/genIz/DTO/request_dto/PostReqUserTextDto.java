package capstoneProject.Lorenzo.genIz.DTO.request_dto;

public class PostReqUserTextDto {
    private String userText;
    private Boolean useLocalModel;

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public Boolean getUseLocalModel() {
        return useLocalModel;
    }

    public void setUseLocalModel(Boolean useLocalModel) {
        this.useLocalModel = useLocalModel;
    }

    
}
