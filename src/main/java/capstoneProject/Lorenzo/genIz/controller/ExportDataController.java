package capstoneProject.Lorenzo.genIz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseDiscussionListDto;
import capstoneProject.Lorenzo.genIz.service.ExportQuizToLocalMachine;
import capstoneProject.Lorenzo.genIz.service.ImplementQuizDaoMethodsInterface;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/export")
public class ExportDataController {

    //inject services to retrieve data from db and export to file
    ImplementQuizDaoMethodsInterface implementQuizDaoMethodsInterface;
    ExportQuizToLocalMachine exportQuizToLocalMachine;

    public ExportDataController(ImplementQuizDaoMethodsInterface implementQuizDaoMethodsInterface,
            ExportQuizToLocalMachine exportQuizToLocalMachine) {
        this.implementQuizDaoMethodsInterface = implementQuizDaoMethodsInterface;
        this.exportQuizToLocalMachine = exportQuizToLocalMachine;
    }
    
    //post controller to pass info and create the local file 
    @PostMapping("/createFile/{chatId}")
    public void postMethodName(@PathVariable Integer chatId) {
        
        //retrieve the discussion list from the current chat
        List<ResponseDiscussionListDto> discussionListDto = implementQuizDaoMethodsInterface.retrieveDiscussions(chatId);

        //create the file with the retrieved discussions
        List<String> quizList = exportQuizToLocalMachine.formatFileContent(discussionListDto);

        exportQuizToLocalMachine.saveFile(quizList);
    }
    

}
