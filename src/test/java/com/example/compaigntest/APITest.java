package com.example.compaigntest;

import com.example.compaigntest.dto.CampaignRequestDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
public class APITest {

    private CampaignRequestDTO requestDTO;
    @BeforeEach
    public void init(){
        requestDTO = new CampaignRequestDTO();

    }
    @Test
    public void checkExceptionTest(){
        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post("/campaign/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptions", is(notNullValue()))
                .body("exceptions.size()",equalTo(2))
                .body("exceptions",containsInAnyOrder(  "Name missing in Campaign",
                        "Product/Bid missing in Campaign"));
    }

    @Test
    public void requestTest(){
        requestDTO.setName("Campaign1");
        Map<Integer, Double> productsWithBids = new HashMap<>();
        productsWithBids.put(1, 10.0);
        productsWithBids.put(2, 9.99);
        requestDTO.setProductsWithBids(productsWithBids);


         given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post("/campaign/new")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Campaign1"))
                .body("startDate", is(requestDTO.getStartDate().toString()));



    }
}
