package capstoneProject.Lorenzo.genIz.DTO.request_dto;

public class PostReqDiscussionDto {
    private int chat_id;
    private String user_pdf_name;
    private String quiz_content;
    
    public int getChat_id() {
        return chat_id;
    }
    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
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

    

}
