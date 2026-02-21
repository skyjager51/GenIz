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

import capstoneProject.Lorenzo.genIz.api_format.ApiCallRequest;

@Service
public class GenerateQuizApi implements GenerateQuizApiInterface{

    //injecting info from .env file 
    @Value("${MODEL_NAME}")
    private String modelName;

    @Value("${MODEL_URL}")
    private String modelUrl;

    //initializing the HTTP client
    HttpClient httpClient = HttpClient.newHttpClient();


    //creating the API request json body 
    @Override
    public String createApiBody() {

        //creating the class 
        ApiCallRequest apiCallRequest = new ApiCallRequest();
        apiCallRequest.setModel(modelName);
        apiCallRequest.addField("system", "you are an expert in CPUs");
        apiCallRequest.addField("user", "explain the difference of performance between 15nm and 3nm");

        //map the class to it's json string 
        Gson gsonRequest = new Gson();
        return gsonRequest.toJson(apiCallRequest);
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
            System.out.println("error: " + e);
        } 

        return httpRequest;
    }

    //retrieving the response by sending the request to the model 
    @Override
    public HttpResponse<String> getApiResponse(HttpRequest apiRequest){
        //using the http client to send the request to the running model 
        HttpResponse<String> response = null;
        try{
            response = httpClient.send(apiRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e){
            System.out.println("error: " + e);
        }

        return response;
    }    

}
