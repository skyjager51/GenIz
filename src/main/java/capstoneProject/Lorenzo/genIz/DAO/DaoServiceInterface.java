package capstoneProject.Lorenzo.genIz.DAO;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import capstoneProject.Lorenzo.genIz.DTO.ChatDataDto;
import capstoneProject.Lorenzo.genIz.DTO.DiscussionDataDto;
import capstoneProject.Lorenzo.genIz.DTO.UserDataDTO;

public interface DaoServiceInterface {

    public UserDataDTO manageUser(OAuth2AuthenticationToken token);

    public ChatDataDto saveChat(UserDataDTO userDataDTO);

    public List<ChatDataDto> retrieveChats(UserDataDTO userDataDTO);

    public DiscussionDataDto saveDiscussion(ChatDataDto chatDataDto);

    public List<DiscussionDataDto> retrieveDiscussions(ChatDataDto chatDataDto);

    public UserDataDTO manageModelSettings(UserDataDTO userDataDTO);

    public UserDataDTO retrieveModelSettings(UserDataDTO userDataDTO);
}
