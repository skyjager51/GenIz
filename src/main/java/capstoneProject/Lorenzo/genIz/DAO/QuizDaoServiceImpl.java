package capstoneProject.Lorenzo.genIz.DAO;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import capstoneProject.Lorenzo.genIz.DTO.ChatDataDto;
import capstoneProject.Lorenzo.genIz.DTO.DiscussionDataDto;
import capstoneProject.Lorenzo.genIz.DTO.UserDataDTO;
import capstoneProject.Lorenzo.genIz.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class QuizDaoServiceImpl implements DaoServiceInterface{

    //inject entity manager to interact with DB 
    EntityManager entityManager;

    public QuizDaoServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //managing user login, if the user already exist, populate the DTO with user data
    //if the user does not exist, create a new user
    @Override
    public UserDataDTO manageUser(OAuth2AuthenticationToken token) {

        String uniqueUserIdentifier = token.getPrincipal().getAttribute("sub");

        UserDataDTO userInfoDto = new UserDataDTO();

        try{
            //try query to identify if the user already exist
            TypedQuery<UserEntity> currentUserQuery = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.identity_provider_user_id = :uniqueId", UserEntity.class);
            
            currentUserQuery.setParameter("uniqueId", uniqueUserIdentifier);

            UserEntity currentUser = currentUserQuery.getSingleResult();

            userInfoDto.setId(currentUser.getId());
            userInfoDto.setIdentity_provider_user_id(currentUser.getIdentity_provider_user_id());
            userInfoDto.setUse_local_model(currentUser.getUse_local_model());

            return userInfoDto;

        } catch (NoResultException e){
            //if the user does not exixt create a new one with the OAuth2 provided info
            UserEntity tempNewUser = new UserEntity();
            tempNewUser.setIdentity_provider_user_id(uniqueUserIdentifier);
            tempNewUser.setUse_local_model(true);

            UserEntity newUser = entityManager.merge(tempNewUser);

            userInfoDto.setId(newUser.getId());
            userInfoDto.setIdentity_provider_user_id(newUser.getIdentity_provider_user_id());
            userInfoDto.setUse_local_model(newUser.getUse_local_model());

            return userInfoDto;

        }
    }

    @Override
    public ChatDataDto saveChat(UserDataDTO userDataDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveChat'");
    }

    @Override
    public List<ChatDataDto> retrieveChats(UserDataDTO userDataDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveChats'");
    }

    @Override
    public DiscussionDataDto saveDiscussion(ChatDataDto chatDataDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveDiscussion'");
    }

    @Override
    public List<DiscussionDataDto> retrieveDiscussions(ChatDataDto chatDataDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveDiscussions'");
    }

    @Override
    public UserDataDTO manageModelSettings(UserDataDTO userDataDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'manageModelSettings'");
    }

    @Override
    public UserDataDTO retrieveModelSettings(UserDataDTO userDataDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveModelSettings'");
    }

}