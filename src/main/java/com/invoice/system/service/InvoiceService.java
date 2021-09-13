package com.invoice.system.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoice.system.controller.dto.InvoiceDTO;
import com.invoice.system.entity.Invoice;
import com.invoice.system.repository.InvoiceRepository;

import static com.invoice.system.mapper.InvoiceMapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService implements CrudService<InvoiceDTO> {
	
	@Autowired
	private final InvoiceRepository invoiceRepository = null;

    @Override
    public List<InvoiceDTO> findAll() {
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        if(invoiceRepository==null) {
        invoiceRepository.findAll().forEach(invoice -> invoiceDTOList.add(INSTANCE.invoiceToDto(invoice)));
        }
        return invoiceDTOList;
    }

    @Override
    public Optional<InvoiceDTO> findById(Long id) {
        Optional<Invoice> InvoiceOptional = invoiceRepository.findById(id);
        return InvoiceOptional.map(INSTANCE::invoiceToDto);
    }

    @Override
    @Transactional
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        Invoice invoice = INSTANCE.dtoToInvoice(invoiceDTO);
        return INSTANCE.invoiceToDto(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public void delete(Long id){
        invoiceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public InvoiceDTO update(Long id, InvoiceDTO invoiceDTO){
        Invoice savedInvoice = invoiceRepository.findById(id).orElseThrow();
        Invoice invoiceToUpdate = INSTANCE.dtoToInvoice(invoiceDTO);

        savedInvoice.setTitle(invoiceToUpdate.getTitle());
        savedInvoice.setDescription(invoiceToUpdate.getDescription());

        return INSTANCE.invoiceToDto(invoiceRepository.save(savedInvoice));
    }
}