package lesson3;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static org.hamcrest.CoreMatchers.is;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.CoreMatchers.equalTo;

import java.io.*;
import java.util.Properties;

public abstract class BaseTest {

    private static String PATH_TO_IMAGE = "src/test/resources/carbon.png";

    static String token;
    static String username;
    static Properties properties = new Properties();

    static String encodedFile;

    static RequestSpecification requestSpecificationWithAuthAndMultipartImage;
    static RequestSpecification requestSpecificationWithAuthAndMultipart64;

    static RequestSpecification requestSpecificationWithAuth;
    static ResponseSpecification responseSpecificationPositive;

    static MultiPartSpecification base64MultiPartSpec;
    static MultiPartSpecification multiPartSpecWithFile;


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
    base64MultiPartSpec = new MultiPartSpecBuilder(encodedFile)
                .controlName("image")
                .build();

    requestSpecificationWithAuthAndMultipart64 = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addMultiPart(base64MultiPartSpec)
                .build();


    multiPartSpecWithFile = new MultiPartSpecBuilder(new File("src/test/resources/carbon.png"))
            .controlName("image")
            .build();


    requestSpecificationWithAuthAndMultipartImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "carbon")
                .addFormParam("type", "png")
                .addMultiPart(multiPartSpecWithFile)
                .build();



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

    public static void getProperties() {
        try (InputStream output = new FileInputStream("src/test/resources/properties.properties")) {
        properties.load(output);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    }
