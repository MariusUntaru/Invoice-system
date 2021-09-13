package com.invoice.system.repository;

import com.invoice.system.entity.Invoice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Integration Tests of InvoiceRepository with H2 Database")
public class InvoiceRepositoryITCase {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Get a Invoice by Id")
    public void givenSingleInvoice_whenFindById_thenGetInvoice() {
        //given

        entityManager.persist(singleInvoice(11L));
        entityManager.flush();
        Invoice savedInvoice = getInvoiceResultListSavedInDb().get(0);

        //when
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(savedInvoice.getId());

        //then
        assertFalse(optionalInvoice.isEmpty());
        assertEquals("Invoice 11", optionalInvoice.get().getTitle());
    }

    @Test
    @DisplayName("Get a list with 3 Invoices")
    public void given3Invoices_whenFindAll_thenGetInvoices() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.persist(singleInvoice(3L));
        entityManager.flush();

        //when
        Iterable<Invoice> invoiceIterable = invoiceRepository.findAll();

        //then
        List<Invoice> invoiceList =
                StreamSupport.stream(invoiceIterable.spliterator(), false)
                        .collect(Collectors.toList());

        assertFalse(invoiceList.isEmpty());
        assertEquals(3, invoiceList.size());
    }

    @Test
    @DisplayName("Get a Invoice by Id when 2 Invoices are in database")
    public void given2Invoices_whenFindById_thenGetSingleInvoice() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.flush();

        Invoice savedInvoice = getInvoiceResultListSavedInDb().get(0);

        //when
        Optional<Invoice> optInvoice = invoiceRepository.findById(savedInvoice.getId());

        //then
        assertTrue(optInvoice.isPresent());
        assertNotNull(optInvoice.get().getId());
    }

    @Test
    @DisplayName("Save a Invoice")
    public void givenSingleInvoice_whenSave_thenInvoiceIsSaved() {
        //given
        Invoice invoice = singleInvoice(1L);

        //when
        invoiceRepository.save(invoice);

        //then
        Invoice savedInvoice = getInvoiceResultListSavedInDb().get(0);
        assertNotNull(savedInvoice.getId());
        assertEquals("Invoice 1", savedInvoice.getTitle());
    }

    @Test
    @DisplayName("Delete Invoice by Id")
    public void given2SavedInvoices_whenDeleteById_thenOnlyOneInvoiceInDb() {
        //given
        entityManager.persist(singleInvoice(1L));
        entityManager.persist(singleInvoice(2L));
        entityManager.flush();

        Invoice deletedInvoice = getInvoiceResultListSavedInDb().get(0);

        //when
        invoiceRepository.deleteById(deletedInvoice.getId());

        //then
        List<Invoice> invoiceList = getInvoiceResultListSavedInDb();

        assertEquals(1, invoiceList.size());
        assertFalse(deletedInvoice.getId().equals(invoiceList.get(0).getId()));
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
}
