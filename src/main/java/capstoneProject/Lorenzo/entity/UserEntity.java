package capstoneProject.Lorenzo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="identity_provider_user_id")
    private String identity_provider_user_id;

    @Column(name="use_local_model")
    private Boolean use_local_model;

    //a user will have multiple chats (bidirectional connection)
    //this is the owned side 
    @OneToMany(mappedBy="defUserEntity", cascade=CascadeType.ALL)
    private List<ChatEntity> chatEntityList;

    
    //method to add the ChatEntities
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getIdentity_provider_user_id() {
        return identity_provider_user_id;
    }


    public void setIdentity_provider_user_id(String identity_provider_user_id) {
        this.identity_provider_user_id = identity_provider_user_id;
    }


    public List<ChatEntity> getChatEntityList() {
        return chatEntityList;
    }


    public void setChatEntityList(List<ChatEntity> chatEntityList) {
        this.chatEntityList = chatEntityList;
    }


    public Boolean getUse_local_model() {
        return use_local_model;
    }
    
    
    public void setUse_local_model(Boolean use_local_model) {
        this.use_local_model = use_local_model;
    }

    
    //method to add new ChatEntities to the list 
    public void addChatEntity(ChatEntity chatEntity){
        if(chatEntityList == null){
            chatEntityList = new ArrayList<>();
        }

        chatEntityList.add(chatEntity);
    }
}
