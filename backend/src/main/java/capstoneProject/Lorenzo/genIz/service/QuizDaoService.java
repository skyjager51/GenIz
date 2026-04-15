package capstoneProject.Lorenzo.genIz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import capstoneProject.Lorenzo.genIz.DAO.QuizDaoImpl;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.ChatDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.DiscussionDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.UserDataDTO;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqChatDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqDiscussionDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqModelSetting;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqOnlineModelDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseChatListDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseDiscussionListDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseModelSettingDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseOnlineModelDto;

@Service
public class QuizDaoService implements ImplementQuizDaoMethodsInterface{

    //injecting the quizDao implementation
    QuizDaoImpl quizDaoImpl;

    public QuizDaoService(QuizDaoImpl quizDaoImpl) {
        this.quizDaoImpl = quizDaoImpl;
    }

    //creating the function to retrieve the Oauth2 token when needed
    public OAuth2AuthenticationToken retrieveToken(){
        return (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }
   
    @Override
    public ResponseChatListDto saveChat(PostReqChatDto postReqChatDto) {

        UserDataDTO currentUser = quizDaoImpl.manageUser(retrieveToken());
        
        //check if chat_name is defined in postReqChatDto, if not stop the execution
        if(postReqChatDto.getChatName() == null){
            throw new IllegalArgumentException("chat name must be defined");
        }

        ChatDataDto currentChat = quizDaoImpl.saveChat(currentUser, postReqChatDto);

        //map the returned chat dto to a response dto
        ResponseChatListDto responseChatDto = new ResponseChatListDto();
        responseChatDto.setChat_id(currentChat.getChat_id());
        responseChatDto.setChat_name(currentChat.getChat_name());

        return responseChatDto;
    }

    @Override
    public List<ResponseChatListDto> retrieveChats() {

        UserDataDTO currentUser = quizDaoImpl.manageUser(retrieveToken());

        //check if the user id is present in userDataDTO, if not stop the execution
        if(currentUser.getId() == null){
            throw new IllegalArgumentException("user id must be defined");
        }

        List<ChatDataDto> currentChats = quizDaoImpl.retrieveChats(currentUser);

        //map the list of chat dto to the response dto that will be sent to the user 
        List<ResponseChatListDto> responseChatsDto = new ArrayList<>();
        for(ChatDataDto chat : currentChats){
            ResponseChatListDto responseChat = new ResponseChatListDto();
            responseChat.setChat_id(chat.getChat_id());
            responseChat.setChat_name(chat.getChat_name());
            responseChatsDto.add(responseChat);
        }

        return responseChatsDto;
    }

    @Override
    public ResponseDiscussionListDto saveDiscussion(PostReqDiscussionDto postReqDiscussionDto) {
        
        //checking the postReqDiscussionDto fields, if not present stop the execution 
        if(postReqDiscussionDto.getChat_id()==null){throw new IllegalArgumentException("chat id must be defined");}
        if(postReqDiscussionDto.getUser_pdf_name()==null){throw new IllegalArgumentException("pdf name must be defined");}
        if(postReqDiscussionDto.getQuiz_content()==null){throw new IllegalArgumentException("quiz content must be defined");}

        DiscussionDataDto currentDuscussion = quizDaoImpl.saveDiscussion(postReqDiscussionDto);

        //map the returned discussion dto into a response dto 
        ResponseDiscussionListDto responseDiscussiondto = new ResponseDiscussionListDto();
        responseDiscussiondto.setDiscussion_id(currentDuscussion.getDiscussion_id());
        responseDiscussiondto.setUser_pdf_name(currentDuscussion.getUser_pdf_name());
        responseDiscussiondto.setQuiz_content(currentDuscussion.getQuiz_content());

        return responseDiscussiondto;
    }

    @Override
    public List<ResponseDiscussionListDto> retrieveDiscussions(Integer chatId) {
        
        //check if the chat id is defined, if not stop execution
        if(chatId == null){
            throw new IllegalArgumentException("chat id must be defined");
        }

        List<DiscussionDataDto> currentDiscussions = quizDaoImpl.retrieveDiscussions(chatId);

        //map the returned list of discussion dtos into a list of response dto
        List<ResponseDiscussionListDto> responseDiscussionsDto = new ArrayList<>();
        for(DiscussionDataDto discussion : currentDiscussions){
            ResponseDiscussionListDto responseDiscussion = new ResponseDiscussionListDto();
            responseDiscussion.setDiscussion_id(discussion.getDiscussion_id());
            responseDiscussion.setUser_pdf_name(discussion.getUser_pdf_name());
            responseDiscussion.setQuiz_content(discussion.getQuiz_content());
            responseDiscussionsDto.add(responseDiscussion);
        }

        return responseDiscussionsDto;
    }

    @Override
    public ResponseModelSettingDto manageModelSettings(PostReqModelSetting postReqModelSetting) {

        UserDataDTO currentUser = quizDaoImpl.manageUser(retrieveToken());
        
        //check if user id and model setting have been defined, if not stop execution 
        if(currentUser.getId()==null){throw new IllegalArgumentException("user id must be defined");}
        if(postReqModelSetting.getModelSetting()==null){throw new IllegalArgumentException("model setting must be defined");}

        UserDataDTO newCurrentUser = quizDaoImpl.manageModelSettings(currentUser, postReqModelSetting);

        //map the current user to return the model setting repsonse
        ResponseModelSettingDto responseModelSettingDto = new ResponseModelSettingDto();
        responseModelSettingDto.setUse_local_model(newCurrentUser.getUse_local_model());

        return responseModelSettingDto;
    }

    @Override
    public ResponseModelSettingDto retrieveModelSettings() {

        UserDataDTO currentUser = quizDaoImpl.manageUser(retrieveToken());
        
        //check if the user id is defined, if not stop execution 
        if(currentUser.getId() == null){
            throw new IllegalArgumentException("user id must be defined");
        }

        UserDataDTO newCurrentUser = quizDaoImpl.retrieveModelSettings(currentUser);

        //map the current user to return the model setting repsonse
        ResponseModelSettingDto responseModelSettingDto = new ResponseModelSettingDto();
        responseModelSettingDto.setUse_local_model(newCurrentUser.getUse_local_model());

        return responseModelSettingDto;
    }

    @Override
    public void deleteChat(Integer chatId) {
        
        //check if chatId id defined, if not stop execution
        if(chatId == null){
            throw new IllegalArgumentException("chat id must be defined");
        }

        //call the delete method
        quizDaoImpl.deleteChat(chatId);
    }

    @Override
    public ResponseChatListDto updateChat(PostReqChatDto postReqChatDto) {
        
        //check if the chat id and chat name are defined 
        if(postReqChatDto.getChat_id()==null){throw new IllegalArgumentException("chat id must be defined");}
        if(postReqChatDto.getChatName()==null){throw new IllegalArgumentException("chat name must be defined");}

        //update the chat by using the dao method
        ChatDataDto updatedChat = quizDaoImpl.updateChat(postReqChatDto);

        //map the updated chat to the resposne dto
        ResponseChatListDto responseChatDto = new ResponseChatListDto();
        responseChatDto.setChat_id(updatedChat.getChat_id());
        responseChatDto.setChat_name(updatedChat.getChat_name());

        return responseChatDto;
    }

    @Override
    public void deleteDiscussion(Integer discussionId) {
        
        //check if discussion id is defined, if not stop execution
        if(discussionId == null){
            throw new IllegalArgumentException("discussion id must be defined");
        }

        //call the delete method
        quizDaoImpl.deleteDiscussion(discussionId);
    }

    @Override
    public void saveNewOnlineModelSettings(PostReqOnlineModelDto postReqOnlineModelDto) {
        
        //no strict format requirements
        quizDaoImpl.saveNewOnlineModelSettings(postReqOnlineModelDto);
    }

    @Override
    public ResponseOnlineModelDto retrieveOnlineModelSettings() {
        
        ResponseOnlineModelDto responseOnlineModelDto = quizDaoImpl.retrieveOnlineModelSettings();

        //validate data existance
        if(responseOnlineModelDto.getModel_url() == null || responseOnlineModelDto.getModel_url() == ""){throw new IllegalArgumentException("Model Url is not defined.");}
        if(responseOnlineModelDto.getModel_name() == null || responseOnlineModelDto.getModel_name() == ""){throw new IllegalArgumentException("Model Name is not defined.");}
        if(responseOnlineModelDto.getApi_key() == null || responseOnlineModelDto.getApi_key() == ""){throw new IllegalArgumentException("Api Key not defined.");}

        return responseOnlineModelDto;
    }

    @Override
    public void deleteOnlineModelSettings() {
        
        //no strict format requirements
        quizDaoImpl.deleteOnlineModelSettings();
    }

}
