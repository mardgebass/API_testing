package lesson3;

import dto.ImageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ImageUploadTest extends BaseTest {

    String uploadedImageId;

    @Test
// не получается с загрузкой формата base64
    void uploadBaseImageTest() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipart64, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
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
                .as(ImageResponse.class)
                .getData().getDeletehash();

    }


    @AfterEach
    void tearDown() {
        given(requestSpecificationWithAuth, responseSpecificationPositive)
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId);
    }


}
