package capstoneProject.Lorenzo.genIz.service;

import com.google.gson.JsonObject;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;

public interface JsonValidationInterface {

    public JsonObject convertStringToJsonObject(ResponseDataDto quizDataDto); 

    public ResponseDataDto validateJsonObject(JsonObject objectToValidate, ResponseDataDto quizDataDto);

}
