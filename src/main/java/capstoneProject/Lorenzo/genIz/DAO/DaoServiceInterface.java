package capstoneProject.Lorenzo.genIz.DAO;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import capstoneProject.Lorenzo.genIz.DTO.entity_dto.ChatDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.DiscussionDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.UserDataDTO;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqChatDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqDiscussionDto;

public interface DaoServiceInterface {

    public UserDataDTO manageUser(OAuth2AuthenticationToken token);

    public ChatDataDto saveChat(UserDataDTO userDataDTO, PostReqChatDto postReqChatDto);

    public List<ChatDataDto> retrieveChats(UserDataDTO userDataDTO);

    public DiscussionDataDto saveDiscussion(PostReqDiscussionDto postReqDiscussionDto);

    public List<DiscussionDataDto> retrieveDiscussions(PostReqDiscussionDto postReqDiscussionDto);

    public UserDataDTO manageModelSettings(UserDataDTO userDataDTO);

    public UserDataDTO retrieveModelSettings(UserDataDTO userDataDTO);
}
