package capstoneProject.Lorenzo.genIz.service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface GenerateQuizApiInterface {

    public String createApiBody();

    public HttpRequest apiCallRequest(String requestBody);

    public HttpResponse<String> getApiResponse(HttpRequest apiRequest);
}
