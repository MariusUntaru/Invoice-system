package com.invoice.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.system.controller.dto.InvoiceDTO;
import com.invoice.system.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController extends CrudController<InvoiceDTO> {

    public InvoiceController(InvoiceService invoiceService) {
        super(invoiceService);
    }
}
