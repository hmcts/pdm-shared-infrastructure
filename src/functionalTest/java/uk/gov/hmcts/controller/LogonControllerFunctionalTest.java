package uk.gov.hmcts.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LogonControllerFunctionalTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(LogonControllerFunctionalTest.class);
    private static final String HEALTHPAGE = "/home";
    private static final String INVALID_STATUS_RESPONSE = "Invalid status response";
    
    @Value("${TEST_URL:http://localhost:8080}")
    private String testUrl;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = getTestUrl();
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    void smokeTest() {
        LOG.info("testUrl={}", getTestUrl());
        Response response = given()
            .contentType(ContentType.JSON)
            .when()
            .get()
            .then()
            .extract().response();
        
        LOG.info("Functionaltest.status={}",response.statusCode());
        Assertions.assertEquals(200, response.statusCode(), INVALID_STATUS_RESPONSE);
    } 
    
    private String getTestUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(testUrl).append(HEALTHPAGE);
        return sb.toString();
    }
}
