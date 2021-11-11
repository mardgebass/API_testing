package lesson3;

import io.restassured.mapper.ObjectMapperType;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImageTest extends BaseTest {

    private final String PATH_TO_IMAGE = "src/test/resources/carbon.png";
    static String encodedFile;
    String uploadedImageId;
    String imageHash;
    String title = "new_title";

    @BeforeEach
    void beforeTest(){
        byte [] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);
    }



//    @Test
//    void uploadFileTest() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", encodedFile)
//                .expect()
//                .body("success", is(true))
//                .body("data.id", is(notNullValue()))
//                .when()
//                .post("https://api.imgur.com/3/image")
//                .prettyPeek()
//                .then()
//                .extract()
//                .response()
//                .jsonPath()
//                .getString("data.deletehash");
//    }

//    @Test
//    void uploadFileImageTest() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", new File("src/test/resources/carbon.png"))
//                .expect()
//                .statusCode(200)
//                .when()
//                .post("https://api.imgur.com/3/upload")
//                .prettyPeek()
//                .then()
//                .extract()
//                .response()
//                .jsonPath()
//                .getString("data.deletehash");
//
//    }

    @Test
    void uploadFileImageTest() {
        imageHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/carbon.png"))
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
    void getImageTest() {
        given()
                .headers("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/image/{imageHash}", imageHash)
                .prettyPeek()
                .then()
                .statusCode(200);

    }

    @Test
    void uploadFileWithoutImageTest() {
        given()
                .headers("Authorization", token)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .statusCode(400);

    }

//    @Test
//    void updateImageInformationTest() {
//        given()
//                .headers("Authorization", token)
////                .multiPart("image", new File("src/test/resources/carbon.png"))
//                .body(title)
//                .expect()
//                .statusCode(200)
//                .when()
//                .post("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
//                .prettyPeek()
//                .then()
//                .extract()
//                .response()
//                .jsonPath()
//                .getString("data.deletehash");
//
//    }


//    @AfterEach
//    void tearDown() {
//        given()
//                .headers("Authorization", token)
//                .when()
//                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId)
//                .prettyPeek()
//                .then()
//                .statusCode(200);
//    }

//    @AfterEach
//    void tearDown() {
//        given()
//                .headers("Authorization", token)
//                .when()
//                .delete("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
//                .prettyPeek()
//                .then()
//                .statusCode(200);
//    }

    private byte[] getFileContent(){
        byte [] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

}
