package lesson3;

import dto.ImageResponce;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class GetImageTest extends BaseTest{

    String uploadedImageId;

    @BeforeEach
    void setUp() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage)
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponce.class)
                .getData().getId();
    }

    @Test
    void getImageInfoTest() {
        given(requestSpecificationWithAuth,responseSpecificationPositive)
                .get("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId);
    }

    @AfterEach
    void tearDown() {
        given(requestSpecificationWithAuth)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}


