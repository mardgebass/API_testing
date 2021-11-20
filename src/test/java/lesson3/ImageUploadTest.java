package lesson3;

import dto.ImageResponce;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ImageUploadTest extends BaseTest {

    String uploadedImageId;




    @Test
    void upload1FileImageTest() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponce.class)
                .getData().getDeletehash();
    }

    @Test
    void uploadFileImageTest() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponce.class)
                .getData().getDeletehash();

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
