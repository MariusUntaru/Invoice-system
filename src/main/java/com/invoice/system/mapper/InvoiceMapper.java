package com.invoice.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.invoice.system.controller.dto.InvoiceDTO;
import com.invoice.system.entity.Invoice;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    InvoiceDTO invoiceToDto(Invoice invoice);
    Invoice dtoToInvoice(InvoiceDTO invoiceDTO);
}
