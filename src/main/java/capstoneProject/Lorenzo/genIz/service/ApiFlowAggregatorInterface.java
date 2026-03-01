package capstoneProject.Lorenzo.genIz.service;

import capstoneProject.Lorenzo.genIz.DTO.ResponseDataDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqUserTextDto;


public interface ApiFlowAggregatorInterface {

    //this service is used to merge the api call logic and the verification logic

    //so i need a method to merform the api call with the given text from the user 
    public ResponseDataDto performApiCall(PostReqUserTextDto postReqUserTextDto);

    //and once the api request if prformet and response recieved i have to verify the result, and
    //if it's all good, i can return the needed dto to respond to ghe user.
    public ResponseDataDto validateApiCallResult(ResponseDataDto tempDTO);

}
