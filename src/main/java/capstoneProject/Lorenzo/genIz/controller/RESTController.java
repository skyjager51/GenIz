package capstoneProject.Lorenzo.genIz.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.service.GenerateQuizApi;

@RestController
public class RESTController {

    //injecting the services utilities 
    private final GenerateQuizApi generateQuizApi;

    public RESTController(GenerateQuizApi generateQuizApi){
        this.generateQuizApi = generateQuizApi;
    }

    @GetMapping("/")
    public String home(){
        return "public endpoint";
    }

    @GetMapping("/private")
    public Principal privatePath(Principal userInfo) {
        return userInfo;
    }

    //sample endpoint for testing purposes, in the prod app it will need to be a POST
    @GetMapping("/ai")
    public String aiResponse() {
        String apiBody = generateQuizApi.createApiBody();

        HttpRequest apiRequest = generateQuizApi.apiCallRequest(apiBody);

        HttpResponse<String> apiResponse = generateQuizApi.getApiResponse(apiRequest);
        
        return apiResponse.body();
    }
}
