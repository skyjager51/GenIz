package capstoneProject.Lorenzo.genIz.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqChatDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqDiscussionDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqModelSetting;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseChatListDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseDiscussionListDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseModelSettingDto;
import capstoneProject.Lorenzo.genIz.service.ImplementQuizDaoMethodsInterface;




@RestController
@RequestMapping("/database/interactions")
public class DataBaseController {

    //inject the service that implements the dao methods 
    ImplementQuizDaoMethodsInterface implementQuizDaoMethodsInterface;

    public DataBaseController(ImplementQuizDaoMethodsInterface implementQuizDaoMethodsInterface) {
        this.implementQuizDaoMethodsInterface = implementQuizDaoMethodsInterface;
    }

    //post controller to save a new chat
    @PostMapping("/save-new-chat")
    public ResponseChatListDto saveChat(@RequestBody PostReqChatDto request) {
        
        ResponseChatListDto savedChat = implementQuizDaoMethodsInterface.saveChat(request);
        
        return savedChat;
    }

    //get controller to retrieve the list of all available chat fo the specified user 
    @GetMapping("/retrieve-all-chats")
    public List<ResponseChatListDto> getAllChats() {
        
        List<ResponseChatListDto> allUserChats = implementQuizDaoMethodsInterface.retrieveChats();

        return allUserChats;
    }
    
    //post controller to save new discussion 
    @PostMapping("/save-new-discussion")
    public ResponseDiscussionListDto saveDiscussion(@RequestBody PostReqDiscussionDto discussion) {
        
        ResponseDiscussionListDto saveDiscussion = implementQuizDaoMethodsInterface.saveDiscussion(discussion);
        
        return saveDiscussion;
    }

    //get controller to retrieve all the discussions related to the chat
    @GetMapping("/retrieve-all-discussions/{chatId}")
    public List<ResponseDiscussionListDto> getAllDiscussions(@PathVariable Integer chatId) {
        
        List<ResponseDiscussionListDto> allChatDiscussions = implementQuizDaoMethodsInterface.retrieveDiscussions(chatId);

        return allChatDiscussions;
    }

    //post controller to save the new model setting 
    @PostMapping("/running-model-setting")
    public ResponseModelSettingDto saveModelSetting(@RequestBody PostReqModelSetting modelSetting) {
        
        ResponseModelSettingDto saveSetting = implementQuizDaoMethodsInterface.manageModelSettings(modelSetting);
        
        return saveSetting;
    }

    //get controller to retrieve current model setting 
    @GetMapping("/retrieve-model-setting")
    public ResponseModelSettingDto getModelSetting() {
        
        ResponseModelSettingDto retrieveSetting = implementQuizDaoMethodsInterface.retrieveModelSettings();

        return retrieveSetting;
    }
    
    //delete controller for deleting the specified chat
    @DeleteMapping("/delete-chat/{chatId}")
    public void deleteChat(@PathVariable Integer chatId){

        implementQuizDaoMethodsInterface.deleteChat(chatId);
    }
    
    //patch controller to modify the chat name
    @PatchMapping("/update-chat-name")
    public ResponseChatListDto patchChat(@RequestBody PostReqChatDto postReqChatDto){

        ResponseChatListDto patchedChat = implementQuizDaoMethodsInterface.updateChat(postReqChatDto);

        return patchedChat;
    }

    //delete controller for deleting the specified discussion
    @DeleteMapping("/delete-discussion/{discussionId}")
    public void deleteDiscussion(@PathVariable Integer discussionId){

        implementQuizDaoMethodsInterface.deleteDiscussion(discussionId);
    }
}
