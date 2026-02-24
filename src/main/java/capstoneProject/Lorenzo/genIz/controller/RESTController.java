package capstoneProject.Lorenzo.genIz.controller;

import java.net.http.HttpRequest;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.QuizDataDto;
import capstoneProject.Lorenzo.genIz.api_format.request.request_parameters.QuizGenParameter;
import capstoneProject.Lorenzo.genIz.service.GenerateQuizApi;

@RestController
public class RESTController {

    //sample user prompt, remove once the necessary controllers will be created 
    @Value("${SAMPLE_USER_PROMPT}")
    private String userString; 

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
    public QuizDataDto aiResponse() {

        //if one of the methods wll return an error, the controller will intercept it and return a server error (500)
        String apiBody = generateQuizApi.createApiBody(QuizGenParameter.getSystemprompt(), userString);

        HttpRequest apiRequest = generateQuizApi.apiCallRequest(apiBody);

        QuizDataDto apiResponse = generateQuizApi.getApiResponse(apiRequest);

        return apiResponse;
    }
}
