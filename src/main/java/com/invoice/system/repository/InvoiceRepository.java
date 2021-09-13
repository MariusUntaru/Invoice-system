package com.invoice.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.invoice.system.entity.Invoice;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
