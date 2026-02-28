package capstoneProject.Lorenzo.genIz.service;


import java.util.List;

import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqChatDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqDiscussionDto;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqModelSetting;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseChatListDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseDiscussionListDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseModelSettingDto;

public interface ImplementQuizDaoMethodsInterface {

    public ResponseChatListDto saveChat(PostReqChatDto postReqChatDto);

    public List<ResponseChatListDto> retrieveChats();

    public ResponseDiscussionListDto saveDiscussion(PostReqDiscussionDto postReqDiscussionDto);

    public List<ResponseDiscussionListDto> retrieveDiscussions(Integer chatId);

    public ResponseModelSettingDto manageModelSettings(PostReqModelSetting postReqModelSetting);

    public ResponseModelSettingDto retrieveModelSettings();
}
