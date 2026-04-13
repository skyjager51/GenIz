package capstoneProject.Lorenzo.genIz.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseOnlineModelDto;
import capstoneProject.Lorenzo.genIz.api_format.request.ApiCallRequest;
import capstoneProject.Lorenzo.genIz.api_format.response.ApiCallResponse;
import jakarta.annotation.PostConstruct;

@Service
public class GenerateQuizApi implements GenerateQuizApiInterface{

    //injcet database service to obtain online model data
    QuizDaoService quizDaoService;

    public GenerateQuizApi(QuizDaoService quizDaoService){
        this.quizDaoService = quizDaoService;
    }

    //injecting info from .env file 
    @Value("${LLM_MODEL}")
    private String modelName;

    @Value("${MODEL_URL}")
    private String modelUrl;

    // @Value("${EXTERNAL_MODEL_API_KEY}")
    // private String modelApiKey;

    // @Value("${EXTERNAL_MODEL_NAME}")
    // private String extModelName;

    // @Value("${EXTERNAL_MODEL_URL}")
    // private String extModelUrl;

    private ResponseOnlineModelDto responseOnlineModelDto;
    
    //populate the DTO after class initialization (neede because quizDaoService must be created before this query)
    @PostConstruct
    private void retrieveOnlineModelSettings(){
        responseOnlineModelDto = quizDaoService.retrieveOnlineModelSettings();
    } 

    //initializing the HTTP client
    HttpClient httpClient = HttpClient.newHttpClient();

    //initializing Gson
    Gson gson = new Gson();


    //creating the API request json body 
    @Override
    public String createApiBody(String systemPrompt, String userText, Boolean useLocalModel) {

        //creating the api body 
        ApiCallRequest apiCallRequest = new ApiCallRequest();
        if(!useLocalModel){
            apiCallRequest.setModel(responseOnlineModelDto.getModel_name());
        } else {
            apiCallRequest.setModel(modelName);
        }
        apiCallRequest.addField("system", systemPrompt);
        apiCallRequest.addField("user", userText);

        //map the class to it's json string 
        String json = gson.toJson(apiCallRequest);

        return json;
    }

    //creating the HTTP request to query the model
    @Override
    public HttpRequest apiCallRequest(String requestBody, Boolean useLocalModel){

        //if useLocal model is not set, use local model
        if(useLocalModel == null){
            useLocalModel = true;
        }

        //initializing the request
        HttpRequest httpRequest = null;

        //if user set useLocalModel to false, use the specified model provier to create the request, else use local model
        if(!useLocalModel){
            try {
                httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(responseOnlineModelDto.getModel_url()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + responseOnlineModelDto.getApi_key())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
    
            } catch (URISyntaxException e) {
                throw new RuntimeException("Something went wrong with the model url", e);
            } 

        } else {
            try {
                httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(modelUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
    
            } catch (URISyntaxException e) {
                throw new RuntimeException("Something went wrong with the model url", e);
            } 
        }

        return httpRequest;
    }

    //retrieving the response by sending the request to the model 
    @Override
    public ResponseDataDto getApiResponse(HttpRequest apiRequest){
        //using the http client to send the request to the running model 
        HttpResponse<String> rawResponse;
        try{
            rawResponse = httpClient.send(apiRequest, HttpResponse.BodyHandlers.ofString());

            //converting the response to a DTO object
            ApiCallResponse convertResponse = gson.fromJson(rawResponse.body(), ApiCallResponse.class);

            return new ResponseDataDto(convertResponse.getChoices().get(0).getMessage().getContent(), 
             convertResponse.getUsage().getTotal_tokens());

        } catch (IOException | InterruptedException | JsonSyntaxException e){
            throw new RuntimeException("Something went wrong when calling the llm model or with the model response format", e);
        }
    }    

}
