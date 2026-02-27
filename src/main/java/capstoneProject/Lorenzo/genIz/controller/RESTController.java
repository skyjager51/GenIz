package capstoneProject.Lorenzo.genIz.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.service.LocalModelApiFlowAggregator;
import capstoneProject.Lorenzo.genIz.service.QuizDaoService;

@RestController
public class RESTController {

    //sample user prompt, remove once the necessary controllers will be created 
    @Value("${SAMPLE_USER_PROMPT}")
    private String userString; 

    //injecting the services utilities 
    LocalModelApiFlowAggregator localModelApiFlowAggregator;
    QuizDaoService quizDaoService;

    public RESTController(LocalModelApiFlowAggregator localModelApiFlowAggregator, QuizDaoService quizDaoService) {
        this.localModelApiFlowAggregator = localModelApiFlowAggregator;
        this.quizDaoService = quizDaoService;
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
    public ResponseDataDto aiResponse() {

        ResponseDataDto performApiCall = localModelApiFlowAggregator.performApiCall(userString);

        ResponseDataDto validateApiResponse = localModelApiFlowAggregator.validateApiCallResult(performApiCall);

        return validateApiResponse;
    }
}
