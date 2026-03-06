package capstoneProject.Lorenzo.genIz.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseDiscussionListDto;

@Service
public class ExportTxtFile implements ExportQuizToLocalMachine{

    @Override
    public List<String> formatFileContent(List<ResponseDiscussionListDto> quizList) {
        
        List<String> discussionList = new ArrayList<>();

        //create the string object that contains the question data
        for(ResponseDiscussionListDto responseDiscussionListDto : quizList){

            //get hold of the quiz content
            String json = responseDiscussionListDto.getQuiz_content();
            
            //extract the json object
            JsonObject rootJson = JsonParser.parseString(json).getAsJsonObject();
            
            //extract the json list
            JsonArray quizzes = rootJson.getAsJsonArray("quizzes");
            
            //loop inside the list and append the element to the Stringbuiler element 
            //then add the element to the discussionList
            for(JsonElement jsonElement : quizzes){

                //create the stringBuilder element
                StringBuilder quizBuilder = new StringBuilder();

                JsonObject quizElements = jsonElement.getAsJsonObject();
                JsonObject options = quizElements.getAsJsonObject("options");

                quizBuilder.append(quizElements.get("question")).append(System.lineSeparator());
                quizBuilder.append("A: ").append(options.get("A")).append(System.lineSeparator());
                quizBuilder.append("B: ").append(options.get("B")).append(System.lineSeparator());
                quizBuilder.append("C: ").append(options.get("C")).append(System.lineSeparator());
                quizBuilder.append("D: ").append(options.get("D")).append(System.lineSeparator());
                quizBuilder.append(quizElements.get("correct_answer")).append(System.lineSeparator());
                quizBuilder.append(quizElements.get("explanation")).append(System.lineSeparator());
                quizBuilder.append(System.lineSeparator());
                quizBuilder.append(System.lineSeparator());

                discussionList.add(quizBuilder.toString());
            }
        }

        return discussionList;
    }

    @Override
    public void saveFile(List<String> fileContent) {

        String path = "";
        String fileName = "";
        
        //take each formatted quiz (if more than 1) and append it at the end of the file 
        try (FileWriter writer = new FileWriter("/Users/lorenzofaccio/desktop/try.txt", true)){
            for (String quiz : fileContent){
                writer.write(quiz);
            }
        } catch (IOException e){
            throw new RuntimeException("the txt creation failed");
        }
    }

}
