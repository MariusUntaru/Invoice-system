package com.invoice.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.system.controller.InvoiceController;
import com.invoice.system.controller.dto.InvoiceDTO;
import com.invoice.system.service.InvoiceService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.invoice.system.util.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
@DisplayName("Unit tests of InvoiceController")
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    @DisplayName("GET an empty list of Invoices")
    public void givenNoInvoices_whenGETInvoices_thenGetEmptyList() throws Exception {
        //given
        when(invoiceService.findAll())
                .thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(
                get("/invoices/")
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("GET a list with single Invoice")
    public void givenSingleInvoice_whenGETInvoices_thenGetSingleInvoiceList() throws Exception {
        //given
        when(invoiceService.findAll())
                .thenReturn(getInvoiceListDTO(1L));

        mockMvc.perform(
                get("/notices/")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].title").value("Invoice 1"))
                .andExpect(jsonPath("[0].description").value("Invoice description 1"));
    }

    @Test
    @DisplayName("GET a Invoice by Id")
    public void givenInvoiceId_whenGETInvoicesById_thenGetSingleInvoice() throws Exception {
        //given
        when(invoiceService.findById(1L))
                .thenReturn(Optional.of(getSingleInvoiceDTO(1L)));

        //when & then
        mockMvc.perform(
                get("/invoices/1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Invoice 1"))
                .andExpect(jsonPath("$.description").value("Invoice description 1"));
    }

    @Test
    @DisplayName("GET a Invoice by Id and return 404 Not Found")
    public void givenIncorrectInvoiceId_whenGETInvoicesById_thenGetNotFoundInvoice() throws Exception {
        //given
        when(invoiceService.findById(1L))
                .thenReturn(Optional.empty());

        //when & then
        mockMvc.perform(
                get("/invoices/1")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST a Invoice to create it")
    public void givenInvoice_whenPOSTSave_thenGetSavedInvoice() throws Exception {
        //given
        InvoiceDTO invoiceDTO = getSingleInvoiceDTO(1L);
        invoiceDTO.setId(null);

        //when
        mockMvc.perform(
                post("/notices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invoiceDTO))
                .characterEncoding("utf-8")

        )

                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("DELETE a Invoice by Id")
    public void givenInvoiceId_whenDELETEInvoice_thenInvoiceIsDeleted() throws Exception {
        //given
        Long invoiceId = 1L;
        when(invoiceService.findById(1L))
                .thenReturn(Optional.of(getSingleInvoiceDTO(1L)));

        //when
        mockMvc.perform(
                delete("/invoices/" + invoiceId)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE a Notice by Id and return 404 HTTP Not Found")
    public void givenInvoiceId_whenDELETEInvoice_thenInvoiceNotFound() throws Exception {
        //given
        Long invoiceId = 1L;
        when(invoiceService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                delete("/invoces/" + invoiceId)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT a Invoce by Id to update it")
    public void givenIdAndUpdatedInvoice_whenPUTUpdate_thenInvoiceIsUpdated() throws Exception {
        //given
        Long invoiceId = 1L;
        InvoiceDTO invoiceDTO = getSingleInvoiceDTO(1L);

        when(invoiceService.findById(1L))
                .thenReturn(Optional.of(invoiceDTO));

        InvoiceDTO updatedInvoice = invoiceDTO;
        updatedInvoice.setTitle("New Title");
        updatedInvoice.setDescription("New Description");

        //when
        mockMvc.perform(
                put("/notices/" + invoiceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invoiceDTO))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Object with id 1 was updated."));

    }

    @Test
    @DisplayName("PUT a Invoice by Id to update it and return 404 HTTP Not Found")
    public void givenIdAndUpdatedInvoice_whenPUTUpdate_thenInvoiceNotFound() throws Exception {
        //given
        Long invoiceId = 1L;
        InvoiceDTO invoiceDTO = getSingleInvoiceDTO(1L);

        when(invoiceService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                put("/invoices/" + invoiceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invoiceDTO))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
