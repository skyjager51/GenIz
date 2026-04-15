package capstoneProject.Lorenzo.genIz.DTO.request_dto;

public class PostReqOnlineModelDto {
    private String model_url;
    private String model_name;
    private String api_key;

    public String getModel_url() {
        return model_url;
    }
    public void setModel_url(String model_url) {
        this.model_url = model_url;
    }
    public String getModel_name() {
        return model_name;
    }
    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
    public String getApi_key() {
        return api_key;
    }
    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }   
}
