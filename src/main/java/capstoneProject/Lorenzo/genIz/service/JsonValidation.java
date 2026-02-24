package capstoneProject.Lorenzo.genIz.service;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import capstoneProject.Lorenzo.genIz.DTO.QuizDataDto;

@Service
public class JsonValidation implements JsonValidationInterface{

    //retrieving the DTO created by the API call and transform it into a json object
    @Override
    public JsonObject convertStringToJsonObject(QuizDataDto quizDataDto) {

        //json returned by the model 
        //since tokenCount is not a critical information, it's accettable to let the application work even without it
        String jsonLlmOutput = quizDataDto.getLlmOutput();

        //if the json is incorrect, parseString will return an exception JsonSyntaxException
        JsonObject rootJson = JsonParser.parseString(jsonLlmOutput).getAsJsonObject();

        return rootJson; 
    }

    //validating the fields inside the API response 
    @Override
    public QuizDataDto validateJsonObject(JsonObject objectToValidate, QuizDataDto quizDataDto) {

        //retrieving the quizzes array and validate content 
        JsonArray quizList = objectToValidate.getAsJsonArray("quizzes");

        for (JsonElement x : quizList){
            JsonObject quizBody = x.getAsJsonObject();

            //validate each quiz present inside the response  
            if (!quizBody.has("question")){throw new IllegalArgumentException("question field has problems");}
            if (!quizBody.has("options")){throw new IllegalArgumentException("options fiels has problems");}

            JsonObject questionOptions = quizBody.getAsJsonObject("options");
            if (!questionOptions.has("A")){throw new IllegalArgumentException("a question option has a problem");}
            if (!questionOptions.has("B")){throw new IllegalArgumentException("a question option has a problem");}
            if (!questionOptions.has("C")){throw new IllegalArgumentException("a question option has a problem");}
            if (!questionOptions.has("D")){throw new IllegalArgumentException("a question option has a problem");}

            if (!quizBody.has("explanation")){throw new IllegalArgumentException("explanation has a problem");}
            if (!quizBody.has("correct_answer")){throw new IllegalArgumentException("answer has a problem");}
        }
        
        //if all checks are passed, return the verified DTO
        return quizDataDto;
    }

}
