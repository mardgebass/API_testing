package lesson3;

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

    @BeforeEach
    void beforeTest(){
        byte [] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);
    }

    @AfterEach
    void clear(){
        given()
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{{deleteHash}}", "testprogmath", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);

    }

    private byte[] getFileContent(){
        byte [] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    @Test
    void imageUpload(){
        uploadedImageId = given()
                .header("Authorization", token)
                .multiPart("Image", encodedFile)
                .expect()
                .statusCode(200)
                .body("data.id", is(notNullValue()))
                .body("data.name", equalTo("frame_name"))
                .body("data.title", equalTo("frame_title"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

    }

}
