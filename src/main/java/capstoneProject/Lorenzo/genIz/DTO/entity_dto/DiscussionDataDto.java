package capstoneProject.Lorenzo.genIz.DTO.entity_dto;

public class DiscussionDataDto {
    private Integer discussion_id; 
    private String user_pdf_name;
    private String quiz_content;
    private Integer chat_id;
    
    public Integer getDiscussion_id() {
        return discussion_id;
    }
    public void setDiscussion_id(Integer discussion_id) {
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
    public Integer getChat_id() {
        return chat_id;
    }
    public void setChat_id(Integer chat_id) {
        this.chat_id = chat_id;
    }

    
}
