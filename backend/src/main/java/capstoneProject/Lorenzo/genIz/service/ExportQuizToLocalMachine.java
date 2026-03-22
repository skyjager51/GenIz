package capstoneProject.Lorenzo.genIz.service;

import java.util.List;

import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseDiscussionListDto;

public interface ExportQuizToLocalMachine {
    public List<String> formatFileContent(List<ResponseDiscussionListDto> quizList);

    public void saveFile(List<String> fileContent);
}
