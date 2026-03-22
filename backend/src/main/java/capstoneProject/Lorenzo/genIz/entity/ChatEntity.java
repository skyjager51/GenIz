package capstoneProject.Lorenzo.genIz.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="chats")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="chat_id")
    private Integer chat_id;

    @Column(name="chat_name")
    private String chat_name;

    //each chat has only one owner (user)
    //this is the owning side
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity defUserEntity;

    //list of all the discussions for the spefic chat
    //this is the owned side 
    @OneToMany(mappedBy="defChatEntity", cascade=CascadeType.ALL)
    private List<DiscussionEntity> discussionEntityList;

    public Integer getChat_id() {
        return chat_id;
    }

    public void setChat_id(Integer chat_id) {
        this.chat_id = chat_id;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public UserEntity getDefUserEntity() {
        return defUserEntity;
    }

    public void setDefUserEntity(UserEntity defUserEntity) {
        this.defUserEntity = defUserEntity;
    }

    public List<DiscussionEntity> getDiscussionEntity() {
        return discussionEntityList;
    }

    public void setDiscussionEntity(List<DiscussionEntity> discussionEntity) {
        this.discussionEntityList = discussionEntity;
    }

    //method to add a discussion to a chat when saved 
    public void addDiscussion(DiscussionEntity discussionEntity){
        if(discussionEntityList == null){
            discussionEntityList = new ArrayList<>();
        }

        discussionEntityList.add(discussionEntity);
    }
}
