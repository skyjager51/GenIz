package capstoneProject.Lorenzo.genIz.service;

import java.net.http.HttpRequest;

import capstoneProject.Lorenzo.genIz.DTO.QuizDataDto;

public interface GenerateQuizApiInterface {

    public String createApiBody();

    public HttpRequest apiCallRequest(String requestBody);

    public QuizDataDto getApiResponse(HttpRequest apiRequest);
}
