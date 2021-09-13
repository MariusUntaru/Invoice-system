package com.invoice.system.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invoice.system.controller.dto.InvoiceDTO;
import com.invoice.system.entity.Invoice;
import com.invoice.system.repository.InvoiceRepository;
import com.invoice.system.service.InvoiceService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.invoice.system.util.TestDataFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of InvoiceService class")
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    @DisplayName("Get an empty list of Invoices")
    public void givenNoInvoices_whenFindAllInvoices_thenGetEmptyList() {
        //given
        when(invoiceRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<InvoiceDTO> invoiceList = invoiceService.findAll();

        //then
        assertEquals(0, invoiceList.size());
    }

    @Test
    @DisplayName("Get a list with single Invoice")
    public void givenSingleInvoices_whenFindAllInvoices_thenSingleInvoiceList() {
        //given
        when(invoiceRepository.findAll())
                .thenReturn(getInvoiceList(1L));

        //when
        List<InvoiceDTO> invoiceList = invoiceService.findAll();

        //then
        assertEquals(1, invoiceList.size());
        assertEquals("Invoice 1", invoiceList.get(0).getTitle());
        assertEquals("Invoice description 1", invoiceList.get(0).getDescription());
    }

    @Test
    @DisplayName("Get a list of 500 Invoices")
    public void given500Invoices_whenFindAllInvoices_then500InvoiceList() {
        //given
        when(invoiceRepository.findAll())
                .thenReturn(getInvoiceList(500L));

        //when
        List<InvoiceDTO> invoiceList = invoiceService.findAll();

        //then
        assertEquals(500, invoiceList.size());
    }

    @Test
    @DisplayName("Get a Invoice by Id")
    public void givenSingleInvoice_whenFindById_thenGetSingleInvoice(){
        //given
        when(invoiceRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleInvoice(1L)));

        //when
        Optional<InvoiceDTO> invoiceDTOOpt = invoiceService.findById(1L);

        //then
        assertTrue(invoiceDTOOpt.isPresent());
        assertEquals("Invoice 1", invoiceDTOOpt.get().getTitle());
        assertEquals("invoice description 1", invoiceDTOOpt.get().getDescription());
    }

    @Test
    @DisplayName("Get a Invoice by Id and return empty result")
    public void givenNoInvoice_whenFindById_thenGetEmptyOptional(){
        //given
        when(invoiceRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<InvoiceDTO> invoiceDTOOpt = invoiceService.findById(1L);

        //then
        assertFalse(invoiceDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Invoice")
    public void givenInvoice_whenSave_thenGetSavedInvoice() {
        //given
        when(invoiceRepository.save(any(Invoice.class)))
                .thenReturn(getSingleInvoice(1L));

        InvoiceDTO invoiceDTO = getSingleInvoiceDTO(1L);

        //when
        InvoiceDTO savedInvoice = invoiceService.save(invoiceDTO);

        //then
        assertNotNull(savedInvoice.getId());
    }

    @Test
    @DisplayName("Update a Invoice")
    public void givenSavedInvoice_whenUpdate_thenInvoiceIsUpdated() {
        //given
        when(invoiceRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleInvoice(1L)));

        when(invoiceRepository.save(any(Invoice.class)))
                .thenReturn(getSingleInvoice(2L));

        InvoiceDTO toBeUpdatedInvoiceDTO = getSingleInvoiceDTO(2L);

        //when
        InvoiceDTO updatedInvoiceDTO = invoiceService.update(1L, toBeUpdatedInvoiceDTO);

        //then
        assertEquals(toBeUpdatedInvoiceDTO.getTitle(), updatedInvoiceDTO.getTitle());
        assertEquals(toBeUpdatedInvoiceDTO.getDescription(), updatedInvoiceDTO.getDescription());
    }
}
