package com.invoice.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.system.controller.VendorController;
import com.invoice.system.controller.dto.VendorDTO;
import com.invoice.system.controller.dto.BoardDTO;
import com.invoice.system.service.VendorService;

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
import java.util.Optional;

import static com.invoice.system.util.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VendorController.class)
@DisplayName("Unit tests of VendorController")
public class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendorService vendorService;

    @Test
    @DisplayName("GET an empty list of Vendors")
    public void givenNoVendors_whenGETfindAll_thenGetEmptyList() throws Exception {
        //given
        when(vendorService.findAll())
                .thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(
                get("/vendors/")
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("GET a list with single Vendor")
    public void givenSingleVendor_whenGETfindAll_thenGetSingleVendorList() throws Exception {
        //given
        when(vendorService.findAll())
                .thenReturn(getVendorListDTO(1L));

        mockMvc.perform(
                get("/vendors/")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].firstName").value("First Name 1"))
                .andExpect(jsonPath("[0].lastName").value("Last Name 1"));
    }

    @Test
    @DisplayName("GET an Vendor by Id")
    public void givenVendorId_whenGETById_thenGetSingleVendor() throws Exception {
        //given
        when(vendorService.findById(1L))
                .thenReturn(Optional.of(getSingleVendorDTO(1L)));

        //when & then
        mockMvc.perform(
                get("/vendors/1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("First Name 1"))
                .andExpect(jsonPath("$.lastName").value("Last Name 1"));
    }

    @Test
    @DisplayName("GET an Vendor by Id and return 404 Not Found")
    public void givenIncorrectVendorId_whenGETById_thenGetNotFoundBoard() throws Exception {
        //given
        when(vendorService.findById(1L))
                .thenReturn(Optional.empty());

        //when & then
        mockMvc.perform(
                get("/vendors/1")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST an Vendor to create it")
    public void givenVendor_whenPOSTSave_thenGetSavedVendor() throws Exception {
        //given
        VendorDTO vendorDTO = getSingleVendorDTO(1L);
        vendorDTO.setId(null);

        //when
        mockMvc.perform(
                post("/vendors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO))
                        .characterEncoding("utf-8")

        )

                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("DELETE an Vendor by Id")
    public void givenVendorId_whenDELETEById_thenVendorIsDeleted() throws Exception {
        //given
        Long vendorId = 1L;
        when(vendorService.findById(1L))
                .thenReturn(Optional.of(getSingleVendorDTO(1L)));

        //when
        mockMvc.perform(
                delete("/vendors/" + vendorId)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE an Vendor by Id and return 404 HTTP Not Found")
    public void givenVendorId_whenDELETEbyId_thenVendorNotFound() throws Exception {
        //given
        Long vendorId = 1L;
        when(vendorService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                delete("/vendors/" + vendorId)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT an Vendor by Id to update it")
    public void givenIdAndUpdatedVendor_whenPUTUpdate_thenVendorIsUpdated() throws Exception {
        //given
        Long vendorId = 1L;
        VendorDTO vendorDTO = getSingleVendorDTO(1L);

        when(vendorService.findById(1L))
                .thenReturn(Optional.of(vendorDTO));

        VendorDTO updatedVendor = vendorDTO;
        updatedVendor.setFirstName("New First Name");
        updatedVendor.setLastName("New Last Name");

        //when
        mockMvc.perform(
                put("/vendors/" + vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedVendor))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Object with id 1 was updated."));

    }

    @Test
    @DisplayName("PUT an Vendor by Id to update it and return 404 HTTP Not Found")
    public void givenIdAndUpdatedVendor_whenPUTUpdate_thenVendorNotFound() throws Exception {
        //given
        Long vendorId = 1L;
        VendorDTO vendorDTO = getSingleVendorDTO(1L);

        when(vendorService.findById(1L))
                .thenReturn(Optional.empty());

        VendorDTO updatedVendor = vendorDTO;
        updatedVendor.setFirstName("New First Name");
        updatedVendor.setLastName("New Last Name");

        //when
        mockMvc.perform(
                put("/vendors/" + vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedVendor))
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
