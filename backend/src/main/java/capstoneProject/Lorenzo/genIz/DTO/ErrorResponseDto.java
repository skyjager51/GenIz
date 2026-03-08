package capstoneProject.Lorenzo.genIz.DTO;

public class ErrorResponseDto {
    public String exceptionErrorMessage;

    public ErrorResponseDto(String exceptionErrorMessage) {
        this.exceptionErrorMessage = exceptionErrorMessage;
    }

    public String getExceptionErrorMessage() {
        return exceptionErrorMessage;
    }

    public void setExceptionErrorMessage(String exceptionErrorMessage) {
        this.exceptionErrorMessage = exceptionErrorMessage;
    }

    
}
