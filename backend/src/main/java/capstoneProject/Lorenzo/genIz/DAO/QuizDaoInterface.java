package capstoneProject.Lorenzo.genIz.DAO;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import capstoneProject.Lorenzo.genIz.DTO.entity_dto.ChatDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.DiscussionDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.UserDataDTO;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqChatDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqDiscussionDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqModelSetting;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqOnlineModelDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseOnlineModelDto;

public interface QuizDaoInterface {

    public UserDataDTO manageUser(OAuth2AuthenticationToken token);

    public ChatDataDto saveChat(UserDataDTO userDataDTO, PostReqChatDto postReqChatDto);

    public List<ChatDataDto> retrieveChats(UserDataDTO userDataDTO);

    public DiscussionDataDto saveDiscussion(PostReqDiscussionDto postReqDiscussionDto);

    public List<DiscussionDataDto> retrieveDiscussions(Integer chatId);

    public UserDataDTO manageModelSettings(UserDataDTO userDataDTO, PostReqModelSetting postReqModelSetting);

    public UserDataDTO retrieveModelSettings(UserDataDTO userDataDTO);

    public void deleteChat(Integer chatId);

    public ChatDataDto updateChat(PostReqChatDto postReqChatDto);

    public void deleteDiscussion(Integer discussionId);

    public void saveNewOnlineModelSettings(PostReqOnlineModelDto postReqOnlineModelDto);

    public ResponseOnlineModelDto retrieveOnlineModelSettings();

    public void deleteOnlineModelSettings(); 
}
