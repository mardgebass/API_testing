package lesson3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImageUploadTest extends BaseTest {

    String imageHash;
    String uploadedImageId;


    @Test
    void upload1FileImageTest() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipart, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadFileImageTest() {
        imageHash = given(requestSpecificationWithAuthAndMultipart64, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

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
