package capstoneProject.Lorenzo.genIz.DTO.entity_dto;

public class UserDataDTO {
    private Integer id;
    private String identity_provider_user_id;
    private Boolean use_local_model;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIdentity_provider_user_id() {
        return identity_provider_user_id;
    }
    public void setIdentity_provider_user_id(String identity_provider_user_id) {
        this.identity_provider_user_id = identity_provider_user_id;
    }
    public Boolean getUse_local_model() {
        return use_local_model;
    }
    public void setUse_local_model(Boolean use_local_model) {
        this.use_local_model = use_local_model;
    }

    
}
