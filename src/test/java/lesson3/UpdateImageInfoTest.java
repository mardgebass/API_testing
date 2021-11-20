package lesson3;

import dto.ImageResponce;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UpdateImageInfoTest extends BaseTest{

    private static final String PATH_TO_IMAGE = "src/test/resources/carbon.png";
    String uploadedImageId;


    @BeforeEach
    void setUp() {
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", new File(PATH_TO_IMAGE))
                .param("title", "title")
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
    void updateImageInfoTest() {
        given()
                .headers("Authorization", token)
                .param("title", "new_title")
                .when()
                .post("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);

        String title = given()
                .headers("Authorization", token)
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
        given(requestSpecificationWithAuth)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    }



