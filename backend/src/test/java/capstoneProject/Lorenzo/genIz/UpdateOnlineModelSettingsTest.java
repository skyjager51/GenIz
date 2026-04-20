package capstoneProject.Lorenzo.genIz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import capstoneProject.Lorenzo.genIz.DAO.QuizDaoImpl;
import capstoneProject.Lorenzo.genIz.DTO.request_dto.PostReqOnlineModelDto;
import capstoneProject.Lorenzo.genIz.DTO.response_dto.ResponseOnlineModelDto;
import jakarta.transaction.Transactional;

@SpringBootTest
@Testcontainers
@Transactional
public class UpdateOnlineModelSettingsTest {

    //create the db container to test db interaction for online model settings
    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4.0");

    @Autowired
    private QuizDaoImpl quizDaoImpl;

    //test to verify that the value getsacutally created
    @Test
    void testSingletonRowPeristance(){
        //create the row
        PostReqOnlineModelDto postReqOnlineModelDto = new PostReqOnlineModelDto();
        postReqOnlineModelDto.setModel_url("https://test-url/v1/completions");
        postReqOnlineModelDto.setModel_name("qwen2.5-3b");
        postReqOnlineModelDto.setApi_key("apiKeyValue1234");
        quizDaoImpl.saveNewOnlineModelSettings(postReqOnlineModelDto);

        //fetch api key back to test if data gets saved correclty
        ResponseOnlineModelDto responseOnlineModelDto = quizDaoImpl.retrieveOnlineModelSettings();
        assertEquals("apiKeyValue1234", responseOnlineModelDto.getApi_key());
    }

    //test to verify that delete command delete all the values in the row without removing the row itself
    @Test
    void testDeleteAllModelInfo(){
        quizDaoImpl.deleteOnlineModelSettings();

        //fetch api key back to test if data gets removed correclty
        ResponseOnlineModelDto responseOnlineModelDto = quizDaoImpl.retrieveOnlineModelSettings();
        assertEquals("null", responseOnlineModelDto.getApi_key());
    }
    
    //test to verify that each field can be modified individually
    @Test
    void testInsertNewApiKeyAndVerifyThatOtherFieldsRemainNull(){
        PostReqOnlineModelDto postReqOnlineModelDto = new PostReqOnlineModelDto();
        postReqOnlineModelDto.setApi_key("apiKey938475");

        //fetch api key back to test if data gets saved correclty
        ResponseOnlineModelDto responseOnlineModelDto = quizDaoImpl.retrieveOnlineModelSettings();
        assertEquals("apiKey938475", responseOnlineModelDto.getApi_key());
    }

    @Test
    void verifyThatOtherFieldsDidNotChanged(){
        //fetch model name to test if data gets handled correclty
        ResponseOnlineModelDto responseOnlineModelDto = quizDaoImpl.retrieveOnlineModelSettings();
        assertEquals("null", responseOnlineModelDto.getModel_name());
    }
}
