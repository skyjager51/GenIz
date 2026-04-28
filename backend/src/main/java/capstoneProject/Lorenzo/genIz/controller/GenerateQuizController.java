package capstoneProject.Lorenzo.genIz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqUserTextDto;
import capstoneProject.Lorenzo.genIz.service.ApiFlowAggregatorInterface;


@RestController
@RequestMapping("/generate-quizzes")
public class GenerateQuizController {

    //injecting services to perform Api calls 
    ApiFlowAggregatorInterface apiFlowAggregatorInterface;


    public GenerateQuizController(ApiFlowAggregatorInterface apiFlowAggregatorInterface) {
        this.apiFlowAggregatorInterface = apiFlowAggregatorInterface;
    }

    @PostMapping("/generate")
    public ResponseDataDto generateQuizzesWithLocalModel(@RequestBody PostReqUserTextDto userTextDto) {
        
        ResponseDataDto apiCallResponse = apiFlowAggregatorInterface.performApiCall(userTextDto);

        ResponseDataDto validateApiResponse = apiFlowAggregatorInterface.validateApiCallResult(apiCallResponse);
        
        return validateApiResponse;
    }
    

}
