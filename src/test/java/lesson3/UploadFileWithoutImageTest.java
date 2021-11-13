package lesson3;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UploadFileWithoutImageTest extends BaseTest {

    @Test
    void uploadFileWithoutImageTest() {
        given(requestSpecificationWithAuth)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .statusCode(400);

    }
}
