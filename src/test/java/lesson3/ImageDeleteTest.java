package lesson3;

import dto.ImageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ImageDeleteTest extends BaseTest{

    String uploadedImageId;
    private static String PATH_TO_IMAGE = "src/test/resources/carbon.png";

    @BeforeEach
    void setUp() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getDeletehash();
    }

    @Test
    void deleteTest() {
        given(requestSpecificationWithAuth, responseSpecificationPositive)
                .delete("https://api.imgur.com/3/image/{imageHash}", uploadedImageId);

        }

}


