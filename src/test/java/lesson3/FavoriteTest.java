package lesson3;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FavoriteTest extends BaseTest{

    String uploadedImageId;
    String deleteHash;
    private Response response;

    @BeforeEach
    void setUp() {
        response = given(requestSpecificationWithAuthAndMultipartImage)
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response();

        uploadedImageId = response.jsonPath().getString("data.id");
        deleteHash = response.jsonPath().getString("data.deletehash");
    }


    @Test
    void favouriteTest(){
        given(requestSpecificationWithAuth,responseSpecificationPositive)
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", uploadedImageId);
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


