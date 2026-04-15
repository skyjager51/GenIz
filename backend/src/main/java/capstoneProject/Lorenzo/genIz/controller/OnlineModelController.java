package capstoneProject.Lorenzo.genIz.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqOnlineModelDto;
import capstoneProject.Lorenzo.genIz.service.ImplementQuizDaoMethodsInterface;

@RestController
@RequestMapping("/online-model/settings")
public class OnlineModelController {

    //inject database methods
    ImplementQuizDaoMethodsInterface implementQuizDaoMethodsInterface;

    public OnlineModelController(ImplementQuizDaoMethodsInterface implementQuizDaoMethodsInterface){
        this.implementQuizDaoMethodsInterface = implementQuizDaoMethodsInterface;
    }

    //post model to save new model settings 
    @PostMapping("/new-settings")
    public void saveNewSettings(@RequestBody PostReqOnlineModelDto postReqOnlineModelDto){
        implementQuizDaoMethodsInterface.saveNewOnlineModelSettings(postReqOnlineModelDto);
    }

    //delete method to remove current model settings 
    @DeleteMapping("/delete-settings")
    public void deleteSettings(){
        implementQuizDaoMethodsInterface.deleteOnlineModelSettings();
    }
}
