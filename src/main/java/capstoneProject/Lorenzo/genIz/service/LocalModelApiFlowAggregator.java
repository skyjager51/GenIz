package capstoneProject.Lorenzo.genIz.service;

import java.net.http.HttpRequest;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqUserTextDto;
import capstoneProject.Lorenzo.genIz.api_format.request.request_parameters.QuizGenParameter;

@Service
public class LocalModelApiFlowAggregator implements ApiFlowAggregatorInterface{

    //injecting services needed to perform business logic 
    GenerateQuizApi generateQuizApi;
    JsonValidation jsonValidation;

    public LocalModelApiFlowAggregator(GenerateQuizApi generateQuizApi, JsonValidation jsonValidation) {
        this.generateQuizApi = generateQuizApi;
        this.jsonValidation = jsonValidation;
    }

    @Override
    public ResponseDataDto performApiCall(PostReqUserTextDto postReqUserTextDto) {
        
        //creating the api body
        String apiBody = generateQuizApi.createApiBody(QuizGenParameter.getSystemprompt(), postReqUserTextDto.getUserText(), postReqUserTextDto.getUseLocalModel());

        //creating the api request
        HttpRequest apiRequest = generateQuizApi.apiCallRequest(apiBody, postReqUserTextDto.getUseLocalModel());

        //send the api request and get a response 
        ResponseDataDto apiResponse = generateQuizApi.getApiResponse(apiRequest);

        return apiResponse;
    }

    @Override
    public ResponseDataDto validateApiCallResult(ResponseDataDto tempDTO) {
        
        //convert the llmOutput from the dto into json format for parsing 
        JsonObject llmOutputConvertedToJson = jsonValidation.convertStringToJsonObject(tempDTO);

        //validate the json format to ensure data presence
        ResponseDataDto validatedModelResponse = jsonValidation.validateJsonObject(llmOutputConvertedToJson, tempDTO);

        return validatedModelResponse;
    }

}
