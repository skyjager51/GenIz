package capstoneProject.Lorenzo.genIz.service;

import com.google.gson.JsonObject;

import capstoneProject.Lorenzo.genIz.DTO.QuizDataDto;

public interface JsonValidationInterface {

    public JsonObject convertStringToJsonObject(QuizDataDto quizDataDto); 

    public QuizDataDto validateJsonObject(JsonObject objectToValidate, QuizDataDto quizDataDto);

}
