package capstoneProject.Lorenzo.genIz.DTO.response_dto;

public class ResponseUserInfoDto {
    private String userPicture;
    private String userName;

    public ResponseUserInfoDto(String userPicture, String userName) {
        this.userPicture = userPicture;
        this.userName = userName;
    }
    
    public String getUserPicture() {
        return userPicture;
    }
    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
}
