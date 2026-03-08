package capstoneProject.Lorenzo.genIz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqUserTextDto;
import capstoneProject.Lorenzo.genIz.service.LocalModelApiFlowAggregator;


@RestController
@RequestMapping("/generate-quizzes")
public class GenerateQuizController {

    //injecting services to perform Api calls 
    LocalModelApiFlowAggregator localModelApiFlowAggregator;

    public GenerateQuizController(LocalModelApiFlowAggregator localModelApiFlowAggregator) {
        this.localModelApiFlowAggregator = localModelApiFlowAggregator;
    }

    @PostMapping("/generate")
    public ResponseDataDto generateQuizzesWithLocalModel(@RequestBody PostReqUserTextDto userTextDto) {
        
        ResponseDataDto apiCallResponse = localModelApiFlowAggregator.performApiCall(userTextDto);

        ResponseDataDto validateApiResponse = localModelApiFlowAggregator.validateApiCallResult(apiCallResponse);
        
        return validateApiResponse;
    }
    

}
