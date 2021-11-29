package lesson3;

import dto.ImageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class UpdateImageInfoTest extends BaseTest{

    String uploadedImageId;

    @BeforeEach
    void setUp() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, responseSpecificationPositive)
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getId();
    }

    @Test
    void updateImageInfoTest() {
        given(requestSpecificationWithAuth)
                .param("title", "new_title")
                .when()
                .post("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);

        String title = given(requestSpecificationWithAuth)
                .when()
                .get("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.title");

        assertThat("title", title, equalTo("new_title"));
    }

    @AfterEach
    void tearDown() {
        given(requestSpecificationWithAuth, responseSpecificationPositive)
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId);
    }

    }



