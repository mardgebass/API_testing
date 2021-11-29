package lesson3;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class FavoriteTest extends BaseTest{

    String uploadedImageId;
    String deleteHash;
    Response response;
    static ResponseSpecification responseSpecificationPositiveFavorite;


    @BeforeEach
    void setUp() {

        responseSpecificationPositiveFavorite = new ResponseSpecBuilder()
                .expectBody("status", equalTo(200))
                .expectBody("data", is("favorited"))
                .expectBody("success", is(true))
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();

        response = given(requestSpecificationWithAuthAndMultipartImage, responseSpecificationPositive)
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
        given(requestSpecificationWithAuth, responseSpecificationPositiveFavorite)
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", uploadedImageId);

    }

    @AfterEach
    void tearDown() {
        given(requestSpecificationWithAuth,responseSpecificationPositive)
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", deleteHash);
    }

    }


