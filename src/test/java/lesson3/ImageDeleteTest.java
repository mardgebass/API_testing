package lesson3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ImageDeleteTest extends BaseTest{

    String uploadedImageId;

    @BeforeEach

    void setUp() {
        uploadedImageId = given(requestSpecificationWithAuth)
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

        @Test
        void deleteTest() {
            given(requestSpecificationWithAuth, responseSpecificationPositive)
                    .delete("https://api.imgur.com/3/image/{imageHash}", uploadedImageId);

        }

}


