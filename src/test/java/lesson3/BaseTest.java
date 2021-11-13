package lesson3;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.io.*;
import java.util.Base64;
import java.util.Properties;

public abstract class BaseTest {

    private static String PATH_TO_IMAGE = "src/test/resources/carbon.png";

    static String token;
    static String username;
    static Properties properties = new Properties();

    static String encodedFile;

    static RequestSpecification requestSpecificationWithAuthAndMultipart;
    static RequestSpecification requestSpecificationWithAuthAndMultipart64;

    static RequestSpecification requestSpecificationWithAuth;
    static ResponseSpecification responseSpecificationPositive;

    private static MultiPartSpecification multiPartSpecification;
    private static MultiPartSpecification multiPartSpecWithFile;


    @BeforeAll
    static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        getProperties();
        token = properties.getProperty("token");
        username = properties.getProperty("username");

        requestSpecificationWithAuth = new RequestSpecBuilder()
            .addHeader("Authorization", token)
            .build();

        responseSpecificationPositive = new ResponseSpecBuilder()
            .expectBody("status", equalTo(200))
            .expectBody("success", is(true))
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .build();



    byte [] byteArray = getFileContent(PATH_TO_IMAGE);
    encodedFile = Base64.getEncoder().encodeToString(byteArray);


    multiPartSpecWithFile = new MultiPartSpecBuilder(new File("src/test/resources/carbon.png"))
            .controlName("carbon")
            .build();

    multiPartSpecification = new MultiPartSpecBuilder(encodedFile)
            .controlName("carbon")
            .build();


    requestSpecificationWithAuthAndMultipart = new RequestSpecBuilder()
            .addHeader("Authorization", token)
            .addMultiPart(multiPartSpecWithFile)
            .addFormParam("title", "title")
            .addFormParam("type", "png")

            .build();

    requestSpecificationWithAuthAndMultipart64 = new RequestSpecBuilder()
            .addHeader("Authorization", token)
            .addMultiPart(multiPartSpecification)
            .build();

}

    public static void getProperties() {
        try (InputStream output = new FileInputStream("src/test/resources/properties.properties")) {
        properties.load(output);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static byte[] getFileContent(String path){
        byte [] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }


    }
