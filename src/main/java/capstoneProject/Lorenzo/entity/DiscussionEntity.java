package capstoneProject.Lorenzo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DiscussionEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="discussion_id")
    private int discussion_id;

    @Column(name="user_pdf_name")
    private String user_pdf_name;

    @Column(name="quiz_content")
    private String quiz_content;

    //each discussion belong to a single chat 
    //this is the owning side 
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="chat_id")
    private ChatEntity defChatEntity;

    public int getDiscussion_id() {
        return discussion_id;
    }

    public void setDiscussion_id(int discussion_id) {
        this.discussion_id = discussion_id;
    }

    public String getUser_pdf_name() {
        return user_pdf_name;
    }

    public void setUser_pdf_name(String user_pdf_name) {
        this.user_pdf_name = user_pdf_name;
    }

    public String getQuiz_content() {
        return quiz_content;
    }

    public void setQuiz_content(String quiz_content) {
        this.quiz_content = quiz_content;
    }

    public ChatEntity getDefChatEntity() {
        return defChatEntity;
    }

    public void setDefChatEntity(ChatEntity defChatEntity) {
        this.defChatEntity = defChatEntity;
    }

    
}
