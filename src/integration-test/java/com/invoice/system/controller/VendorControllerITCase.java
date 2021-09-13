package com.invoice.system.controller;

import com.google.gson.JsonObject;
import com.wkrzywiec.medium.noticeboard.config.TestDataProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@DisplayName("Integration Tests of the Vendor CRUD REST endpoints")
public class VendorControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 5 Vendors")
    public void whenGETfindAll_thenGetListOf5Vendors() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/vendors/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(2));
    }

    @Test
    @DisplayName("GET an Vendor by Id")
    public void givenVendorId_thenGetSingleVendor() {
        //given
        Long vendorId = 2L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/vendors/" + authorId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(2))
                .body("firstName", equalTo("Jane"))
                .body("lastName", equalTo("Doe"));
    }

    @Test
    @DisplayName("POST an Vendor to create it")
    public void givenVendor_whenPOSTSave_thenGetSavedVendor(){
        //given
        JsonObject vendorJson = TestDataProvider.getVendorJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(vendorJson.toString())

                .when()
                .post(baseURL + "/vendors/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Snow"));
    }

    @Test
    @DisplayName("DELETE an Vendor by Id")
    public void givenVendorId_whenDELETEbyId_thenVendorIsDeleted() {
        //given
        Long vendorId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/vendors/" + vendorId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT an Vendor by Id to update it")
    public void givenIdAndUpdatedVendor_whenPUTUpdate_thenVendorIsUpdated() {
        //given
        Long vendorId = 3L;
        JsonObject vendorJson = TestDataProvider.getVendorJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(vendorJson.toString())

                .when()
                .put(baseURL + "/vendors/" + vendorId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
