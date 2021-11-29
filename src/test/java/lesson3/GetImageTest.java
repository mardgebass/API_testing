package lesson3;

import dto.ImageResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetImageTest extends BaseTest{

    String uploadedImageId;
    String deleteHash;
    Response response;

    @BeforeEach
    void setUp() {
        response = given(requestSpecificationWithAuthAndMultipartImage, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response();

        uploadedImageId = response.jsonPath().getString("data.id");
        deleteHash = response.jsonPath().getString("data.deletehash");
    }

    @Test
    void getImageInfoTest() {
        given(requestSpecificationWithAuth, responseSpecificationPositive)
                .get("https://api.imgur.com/3/image/{imageHash}", uploadedImageId)
                .prettyPeek();
    }


    // не работает с импользованием responseSpecificationPositive
    @AfterEach
    void tearDown() {
        given(requestSpecificationWithAuth,responseSpecificationPositive)
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", deleteHash);
    }

}


