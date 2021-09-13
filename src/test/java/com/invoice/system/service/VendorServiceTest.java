package com.invoice.system.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invoice.system.controller.dto.VendorDTO;
import com.invoice.system.entity.Vendor;
import com.invoice.system.repository.VendorRepository;
import com.invoice.system.service.VendorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.invoice.system.util.TestDataFactory.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of VendorService class")
public class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorService vendorService;

    @Test
    @DisplayName("Get an empty list of Vendor")
    public void givenNoVendors_whenFindAll_thenGetEmptyList() {
        //given
        when(vendorRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<VendorDTO> vendorList = vendorService.findAll();

        //then
        assertEquals(0, vendorList.size());
    }

    @Test
    @DisplayName("Get a list with single Vendor")
    public void givenSingleVendors_whenFindAll_thenSingleVendorList() {
        //given
        when(vendorRepository.findAll())
                .thenReturn(getVendorList(1L));

        //when
        List<VendorDTO> vendorList = vendorService.findAll();

        //then
        assertEquals(1, vendorList.size());
        assertEquals("First Name 1", vendorList.get(0).getFirstName());
        assertEquals("Last Name 1", vendorList.get(0).getLastName());
    }

    @Test
    @DisplayName("Get a list of 500 Vendor")
    public void given500Vendors_whenFindAll_then500VendorList() {
        //given
        when(vendorRepository.findAll())
                .thenReturn(getVendorList(500L));

        //when
        List<VendorDTO> vendorList = vendorService.findAll();

        //then
        assertEquals(500, vendorList.size());
    }

    @Test
    @DisplayName("Get an Vendor by Id")
    public void givenSingleVendor_whenFindById_thenGetSingleVendor(){
        //given
        when(vendorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleVendor(1L)));

        //when
        Optional<VendorDTO> vendorOpt = vendorService.findById(1L);

        //then
        assertTrue(vendorOpt.isPresent());
        assertNotNull(vendorOpt.get().getId());
        assertEquals("First Name 1", vendorOpt.get().getFirstName());
        assertEquals("Last Name 1", vendorOpt.get().getLastName());
    }

    @Test
    @DisplayName("Get an Vendor by Id and return empty result")
    public void givenNoVendor_whenFindById_thenGetEmptyOptional(){
        //given
        when(vendorRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<VendorDTO> vendorOpt = vendorService.findById(1L);

        //then
        assertFalse(vendorOpt.isPresent());
    }

    @Test
    @DisplayName("Save an Vendor")
    public void givenVendor_whenSave_thenGetSavedVendor() {
        //given
        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(getSingleVendor(1L));

        VendorDTO vendorDTO = getSingleVendorDTO(1L);

        //when
        VendorDTO savedVendor = vendorService.save(vendorDTO);

        //then
        assertNotNull(savedVendor.getId());
    }

    @Test
    @DisplayName("Update an Vendor")
    public void givenSavedVendor_whenUpdate_thenVendorIsUpdated() {
        //given
        when(vendorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleVendor(1L)));

        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(getSingleVendor(2L));

        VendorDTO toBeUpdatedVendorDTO = getSingleVendorDTO(2L);

        //when
        VendorDTO updatedVendorDTO = vendorService.update(1L, toBeUpdatedVendorDTO);

        //then
        assertEquals(toBeUpdatedVendorDTO.getFirstName(), updatedVendorDTO.getFirstName());
        assertEquals(toBeUpdatedVendorDTO.getLastName(), updatedVendorDTO.getLastName());
    }
}
