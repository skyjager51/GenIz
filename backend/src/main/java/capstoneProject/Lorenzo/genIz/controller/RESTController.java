package capstoneProject.Lorenzo.genIz.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseUserInfoDto;

@RestController
public class RESTController {

    //retrieving user info to populate user details in frontend
    @GetMapping("/user-info")
    public ResponseUserInfoDto privatePath(OAuth2AuthenticationToken token) {

        String userPicture = token.getPrincipal().getAttribute("picture");
        String userName = token.getPrincipal().getAttribute("name");

        ResponseUserInfoDto userInfoDto = new ResponseUserInfoDto(userPicture, userName);

        return userInfoDto;
    }

}
