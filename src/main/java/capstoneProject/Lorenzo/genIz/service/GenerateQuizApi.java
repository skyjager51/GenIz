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

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.api_format.request.ApiCallRequest;
import capstoneProject.Lorenzo.genIz.api_format.response.ApiCallResponse;

@Service
public class GenerateQuizApi implements GenerateQuizApiInterface{

    //injecting info from .env file 
    @Value("${MODEL_NAME}")
    private String modelName;

    @Value("${MODEL_URL}")
    private String modelUrl;

    //initializing the HTTP client
    HttpClient httpClient = HttpClient.newHttpClient();

    //initializing Gson
    Gson gson = new Gson();


    //creating the API request json body 
    @Override
    public String createApiBody(String systemPrompt, String userText) {

        //creating the class 
        ApiCallRequest apiCallRequest = new ApiCallRequest();
        apiCallRequest.setModel(modelName);
        apiCallRequest.addField("system", systemPrompt);
        apiCallRequest.addField("user", userText);

        //map the class to it's json string 
        return gson.toJson(apiCallRequest);
    }

    //creating the HTTP request to query the model
    @Override
    public HttpRequest apiCallRequest(String requestBody){
        //initializing the request
        HttpRequest httpRequest = null;
        try {
            httpRequest = HttpRequest.newBuilder()
                .uri(new URI(modelUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        } catch (URISyntaxException e) {
            throw new RuntimeException("something went wrong", e);
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

        } catch (IOException | InterruptedException e){
            throw new RuntimeException("something went wrong", e);
        }
    }    

}
