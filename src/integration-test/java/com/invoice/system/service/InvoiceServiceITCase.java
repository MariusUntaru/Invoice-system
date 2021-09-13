package com.invoice.system.service;

import com.invoice.system.controller.dto.InvoiceDTO;
import com.wkrzywiec.medium.invoiceboard.entity.Invoice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Integration Tests of InvoiceService with H2 Database")
public class InvoiceServiceITCase {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("Get a list with 3 Invoices")
    public void given3InvoicesInDb_whenFindAll_thenGet3Invoices() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.persist(singleInvoice(3L));
        entityManager.flush();

        //when
        List<InvoiceDTO> invoiceList = invoiceService.findAll();

        //then
        assertEquals(3, invoiceList.size());
    }

    @Test
    @Transactional
    @DisplayName("Get a list with single Invoice")
    public void given3InvoicesInDb_whenFindById_thenGetSingleInvoice() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.persist(singleInvoice(3L));
        entityManager.flush();

        Invoice savedInvoice = getInvoiceResultListSavedInDb().get(0);


        //when
        Optional<InvoiceDTO> invoiceDTOOpt = invoiceService.findById(savedInvoice.getId());

        //then
        assertTrue(invoiceDTOOpt.isPresent());
        assertEquals(savedInvoice.getId(), invoiceDTOOpt.get().getId());
        assertEquals(savedInvoice.getTitle(), invoiceDTOOpt.get().getTitle());
        assertEquals(savedInvoice.getDescription(), invoiceDTOOpt.get().getDescription());
    }

    @Test
    @Transactional
    @DisplayName("Get a Invoice by Id and return empty result")
    public void given3InvoicesInDb_whenFindById_thenGetEmptyOptional() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.persist(singleInvoice(3L));
        entityManager.flush();

        //when
        Optional<InvoiceDTO> invoiceDTOOpt = invoiceService.findById(5000L);

        //then
        assertTrue(invoiceDTOOpt.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Save a Invoice")
    public void givenInvoice_whenSave_thenGetSavedInvoice() {
        //given
        InvoiceDTO invoice = singleInvoiceDTO(1L);

        //when
        InvoiceDTO savedInvoice = invoiceService.save(invoice);

        //then
        assertNotNull(savedInvoice.getId());
        assertEquals(invoice.getTitle(), savedInvoice.getTitle());
        assertEquals(invoice.getDescription(), savedInvoice.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("Delete Invoice by Id")
    public void given3InvoicesInDb_whenDeleteById_thenGet2Invoices() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.persist(singleInvoice(3L));
        entityManager.flush();
        Invoice savedInvoice = getInvoiceResultListSavedInDb().get(0);

        //when
        invoiceService.delete(savedInvoice.getId());

        //then
        List<Invoice> invoiceDTOList = getInvoiceResultListSavedInDb();
        assertEquals(2, invoiceDTOList.size());
    }

    @Test
    @Transactional
    @DisplayName("Update a Invoice")
    public void givenSavedInvoiceInDb_whenUpdate_thenInvoiceIsUpdated() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.flush();
        Invoice savedInvoice = getInvoiceResultListSavedInDb().get(0);

        InvoiceDTO toUpdateInvoiceDTO = singleInvoiceDTO(2L);

        //when
        InvoiceDTO updatedInvoiceDTO = invoiceService.update(savedInvoice.getId(), toUpdateInvoiceDTO);

        //then
        assertEquals(toUpdateInvoiceDTO.getTitle(), updatedInvoiceDTO.getTitle());
        assertEquals(toUpdateInvoiceDTO.getDescription(), updatedInvoiceDTO.getDescription());
        assertEquals(savedInvoice.getId(), updatedInvoiceDTO.getId());
    }

    private Invoice singleInvoice(Long number){
        return Invoice.builder()
                .title("Invoice " + number)
                .description("Invoice description " + number)
                .build();
    }

    private List<Invoice> getInvoiceResultListSavedInDb() {
        return entityManager
                .createNativeQuery("SELECT Invoice.* FROM Invoice", Invoice.class)
                .getResultList();
    }

    private InvoiceDTO singleInvoiceDTO(Long number){
        return InvoiceDTO.builder()
                .title("Invoice " + number)
                .description("Invoice description " + number)
                .build();
    }
}
