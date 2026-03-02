package capstoneProject.Lorenzo.genIz.service;

import java.net.http.HttpRequest;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;

public interface GenerateQuizApiInterface {

    public String createApiBody(String systemPrompt, String userText);

    public HttpRequest apiCallRequest(String requestBody, Boolean useLocalModel);

    public ResponseDataDto getApiResponse(HttpRequest apiRequest);
}
