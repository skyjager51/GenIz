package capstoneProject.Lorenzo.genIz.api_format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiCallRequest {
    private String model;
    private List<Map<String, String>> messages = new ArrayList<>();

    //contructor
    public ApiCallRequest(){

    }

    //getter & setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }

    //function to populate the messages list, each entry is composed of 2 elements, role and content
    public void addField(String role, String content){
        Map<String, String> field = new HashMap<>();
        field.put("role", role);
        field.put("content", content);

        messages.add(field);
    }
    

    
}
