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

@DisplayName("Integration Tests of the InvoiceController REST endpoints")
public class InvoceiceControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 4 Invoices")
    public void whenGETAllInvoices_thenGetListOf4Invoices() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/invoices/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    @DisplayName("GET a Invoice by Id")
    public void givenInvoiceId_thenGetSingleInvoice() {
        //given
        Long invoiceId = 1L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/invoices/" + invoiceId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("title", equalTo("Invoice 1 title"))
                .body("description", equalTo("Invoice 1 description"))
                .body("author.id", equalTo(1))
                .body("author.firstName", equalTo("John"))
                .body("author.lastName", equalTo("Doe"));
    }

    @Test
    @DisplayName("POST a Invoice to create it")
    public void givenInvoice_whenPOSTSave_thenGetSavedInvoice(){
        //given
        JsonObject invoiceJson = TestDataProvider.getInvoiceJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(invoiceJson.toString())

                .when()
                .post(baseURL + "/invoices/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
//                .body("creationDate", notNullValue())
                .body("title", equalTo("Invoice TEST title"))
                .body("description", equalTo("Invoice TEST description"));
    }

    @Test
    @DisplayName("DELETE a Invoice by Id")
    public void givenInvoiceId_whenDELETEInvoice_thenInvoiceIsDeleted() {
        //given
        Long invoiceId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/invoices/" + invoiceId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT a Invoice by Id to update it")
    public void givenIdAndUpdatedInvoice_whenPUTUpdate_thenInvoiceIsUpdated() {
        //given
        Long invoiceId = 4L;
        JsonObject invoiceJson = TestDataProvider.getInvoiceJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(invoiceJson.toString())

                .when()
                .put(baseURL + "/invoices/" + invoiceId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
