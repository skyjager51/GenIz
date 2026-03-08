package capstoneProject.Lorenzo.genIz.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqUserTextDto;
import capstoneProject.Lorenzo.genIz.service.LocalModelApiFlowAggregator;

@RestController
public class RESTController {

    //injecting the services utilities 
    LocalModelApiFlowAggregator localModelApiFlowAggregator;

    public RESTController(LocalModelApiFlowAggregator localModelApiFlowAggregator) {
        this.localModelApiFlowAggregator = localModelApiFlowAggregator;
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
    public ResponseDataDto generateQuizzesWithLocalModel(@RequestBody PostReqUserTextDto userTextDto) {
        
        ResponseDataDto apiCallResponse = localModelApiFlowAggregator.performApiCall(userTextDto);

        ResponseDataDto validateApiResponse = localModelApiFlowAggregator.validateApiCallResult(apiCallResponse);
        
        return validateApiResponse;
    }
}
