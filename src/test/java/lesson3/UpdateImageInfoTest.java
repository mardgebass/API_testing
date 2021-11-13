package lesson3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UpdateImageInfoTest extends BaseTest{

    String uploadedImageId;

    @BeforeEach

    void setUp() {
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/carbon.png"))
                .param("title", "title")
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @Order(1)
    void updateImageInfoTest() {
        given()
                .headers("Authorization", token)
                .param("title", "new_title")
                .when()
                .post("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void getImageInfoTest() {
        given()
                .headers("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}


