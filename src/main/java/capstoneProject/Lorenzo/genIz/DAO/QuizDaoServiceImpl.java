package capstoneProject.Lorenzo.genIz.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Repository;

import capstoneProject.Lorenzo.genIz.DTO.entity_dto.ChatDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.DiscussionDataDto;
import capstoneProject.Lorenzo.genIz.DTO.entity_dto.UserDataDTO;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqChatDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqDiscussionDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqModelSetting;
import capstoneProject.Lorenzo.genIz.entity.ChatEntity;
import capstoneProject.Lorenzo.genIz.entity.DiscussionEntity;
import capstoneProject.Lorenzo.genIz.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class QuizDaoServiceImpl implements DaoServiceInterface{

    //inject entity manager to interact with DB 
    EntityManager entityManager;

    public QuizDaoServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //managing user login, if the user already exist, populate the DTO with user data
    //if the user does not exist, create a new user
    @Override
    @Transactional
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

    //create a new chat based on the user id and the chat name
    @Override
    @Transactional
    public ChatDataDto saveChat(UserDataDTO userDataDTO, PostReqChatDto postReqChatDto) {

        //find the user based on the userDto
        UserEntity currentUser = entityManager.find(UserEntity.class, userDataDTO.getId());
        if(currentUser == null){
            throw new IllegalArgumentException("no user found");
        }
        
        //create the dto for data transfer
        ChatDataDto chatInfoDto = new ChatDataDto();

        //create the chat entity
        ChatEntity tempNewChat = new ChatEntity();
        tempNewChat.setChat_name(postReqChatDto.getChatName());

        //create the relation between chat and user entity
        tempNewChat.setDefUserEntity(currentUser);
        currentUser.addChatEntity(tempNewChat);

        //save the new chat
        ChatEntity newChatEntity = entityManager.merge(tempNewChat);

        //map the dto and return the new data
        chatInfoDto.setChat_id(newChatEntity.getChat_id());
        chatInfoDto.setChat_name(newChatEntity.getChat_name());
        chatInfoDto.setUser_id(currentUser.getId());

        return chatInfoDto;
    }

    //retrieve all the chats owned by the specific users
    @Override
    public List<ChatDataDto> retrieveChats(UserDataDTO userDataDTO) {
        
        //find the user based on the userDto
        UserEntity currentUser = entityManager.find(UserEntity.class, userDataDTO.getId());
        if(currentUser == null){
            throw new NoResultException("no user found");
        }

        //query the db to return only chats that are owned by the specific user
       
        TypedQuery<ChatEntity> chatQuery = entityManager.createQuery(
            "SELECT c FROM ChatEntity c WHERE c.defUserEntity.id = :userId", ChatEntity.class);
        
        chatQuery.setParameter("userId", userDataDTO.getId());

        //retrieve the list of chats
        List<ChatEntity> userChats = chatQuery.getResultList();
        
        //map the chatEntity to a list of dto
        List<ChatDataDto> listUserChatsDto = new ArrayList<>();
        for(ChatEntity chat : userChats){
            ChatDataDto tempChatDataDto = new ChatDataDto();
            tempChatDataDto.setChat_id(chat.getChat_id());
            tempChatDataDto.setChat_name(chat.getChat_name());
            tempChatDataDto.setUser_id(userDataDTO.getId());
            listUserChatsDto.add(tempChatDataDto);
        }

        return listUserChatsDto;
        
    }

    //save the current generated quiz discussion 
    @Override
    @Transactional
    public DiscussionDataDto saveDiscussion(PostReqDiscussionDto postReqDiscussionDto) {
        
        //retrieve the chat where to save the discussion 
        ChatEntity currentChat = entityManager.find(ChatEntity.class, postReqDiscussionDto.getChat_id());

        if(currentChat == null){
            throw new NoResultException("no chat found");
        }

        //create a new discussion
        DiscussionEntity tempDiscussionEntity = new DiscussionEntity();

        tempDiscussionEntity.setUser_pdf_name(postReqDiscussionDto.getUser_pdf_name());
        tempDiscussionEntity.setQuiz_content(postReqDiscussionDto.getQuiz_content());

        //create the relation between the cat and the discussion 
        currentChat.addDiscussion(tempDiscussionEntity);
        tempDiscussionEntity.setDefChatEntity(currentChat);

        //save the new discussion 
        DiscussionEntity currentDiscussion = entityManager.merge(tempDiscussionEntity);

        //create the dto and return it
        DiscussionDataDto currentDiscussionDataDto = new DiscussionDataDto();
        currentDiscussionDataDto.setDiscussion_id(currentDiscussion.getDiscussion_id());
        currentDiscussionDataDto.setUser_pdf_name(currentDiscussion.getUser_pdf_name());
        currentDiscussionDataDto.setQuiz_content(currentDiscussion.getQuiz_content());
        currentDiscussionDataDto.setChat_id(postReqDiscussionDto.getChat_id());

        return currentDiscussionDataDto;
    }

    //retrieve all the discussions of a chat
    @Override
    public List<DiscussionDataDto> retrieveDiscussions(PostReqDiscussionDto postReqDiscussionDto) {
        
        //retrieve the chat that holds the requested conversations
        ChatEntity currentChat = entityManager.find(ChatEntity.class, postReqDiscussionDto.getChat_id());

        if(currentChat == null){
            throw new NoResultException("no chat found");
        }

        //retrieve all the discussions related to the chat
        TypedQuery<DiscussionEntity> discussionQuery = entityManager.createQuery(
            "SELECT d FROM DiscussionEntity d WHERE d.defChatEntity.chat_id = :currentChatId", DiscussionEntity.class);
        
        discussionQuery.setParameter("currentChatId", postReqDiscussionDto.getChat_id());

        //query the db
        List<DiscussionEntity> chatDiscussions = discussionQuery.getResultList();
        
        //create the discussionDto list
        List<DiscussionDataDto> listChatDiscussionDto = new ArrayList<>();
        for(DiscussionEntity discussion : chatDiscussions){
            DiscussionDataDto discussionDataDto = new DiscussionDataDto();
            discussionDataDto.setDiscussion_id(discussion.getDiscussion_id());
            discussionDataDto.setUser_pdf_name(discussion.getUser_pdf_name());
            discussionDataDto.setQuiz_content(discussion.getQuiz_content());
            discussionDataDto.setChat_id(postReqDiscussionDto.getChat_id());

            listChatDiscussionDto.add(discussionDataDto);
        } 

        return listChatDiscussionDto;
    }

    //toggle the switch to tell the application to use the local model or the specified Internet one
    @Override
    @Transactional
    public UserDataDTO manageModelSettings(UserDataDTO userDataDTO, PostReqModelSetting postReqModelSetting) {
        
        //retrieve the current user
        UserEntity currentUser = entityManager.find(UserEntity.class, userDataDTO.getId());

        //if the request is acutally changing the setting, do the modification, else do nothing
        if(!currentUser.getUse_local_model().equals(postReqModelSetting.getModelSetting())){
            currentUser.setUse_local_model(postReqModelSetting.getModelSetting());
        }

        //save the new user and map it with the new dto 
        currentUser = entityManager.merge(currentUser);

        UserDataDTO newUserDataDTO = new UserDataDTO();
        newUserDataDTO.setId(currentUser.getId());
        newUserDataDTO.setIdentity_provider_user_id(currentUser.getIdentity_provider_user_id());
        newUserDataDTO.setUse_local_model(currentUser.getUse_local_model());

        return newUserDataDTO;
    }

    //retrieve the current model setting 
    @Override
    public UserDataDTO retrieveModelSettings(UserDataDTO userDataDTO) {
        
        //retrieve the current user info 
        UserEntity currentUser = entityManager.find(UserEntity.class, userDataDTO.getId());

        //map the user dto 
        UserDataDTO newUserDataDTO = new UserDataDTO();
        newUserDataDTO.setId(currentUser.getId());
        newUserDataDTO.setIdentity_provider_user_id(currentUser.getIdentity_provider_user_id());
        newUserDataDTO.setUse_local_model(currentUser.getUse_local_model());

        return newUserDataDTO;
    }

}