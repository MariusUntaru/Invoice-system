package com.invoice.system.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.invoice.system.controller.dto.VendorDTO;
import com.invoice.system.controller.dto.BoardDTO;
import com.invoice.system.controller.dto.InvoiceDTO;
import com.invoice.system.entity.Vendor;
import com.invoice.system.entity.Board;
import com.invoice.system.entity.Invoice;

public class TestDataFactory {

    public static Invoice getSingleInvoice(Long id){
        return Invoice.builder()
                .id(id)
                .title("Invoice " + id)
                .description("Invoice description " + id)
                .build();
    }

    public static List<InvoiceDTO> getInvoiceList(Long invoicesCount){
        return LongStream.rangeClosed(1, invoicesCount)
                .mapToObj(TestDataFactory::getSingleInvoiceDTO)
                .collect(Collectors.toList());
    }

    public static List<InvoiceDTO> getInvoiceListDTO(Long invoicesCount) {
        return LongStream.rangeClosed(1, invoicesCount)
                .mapToObj(TestDataFactory::getSingleInvoiceDTO)
                .collect(Collectors.toList());
    }

    public static InvoiceDTO getSingleInvoiceDTO(Long id){
        return InvoiceDTO.builder()
                .title("Invoice " + id)
                .description("Invoice description " + id)
                .build();
    }

    public static Board getSingleBoard(Long id, Long invoicesCount){
        return Board.builder()
                .id(id)
                .title("Board " + id)
                .noticeList(getInvoiceList(invoicesCount))
                .build();
    }

    public static List<Board> getBoardList(Long boardsCount, Long invoicesCount){
        return LongStream.rangeClosed(1, boardsCount)
                .mapToObj(id -> getSingleBoard(id, invoicesCount))
                .collect(Collectors.toList());
    }

    public static BoardDTO getSingleBoardDTO(Long id, Long invoicesCount){
        return BoardDTO.builder()
                .id(id)
                .title("Board " + id)
                .invoiceList(getInvoiceListDTO(invoicesCount))
                .build();
    }

    public static List<BoardDTO> getBoardListDTO(Long boardsCount, Long invoicesCount){
        return LongStream.rangeClosed(1, boardsCount)
                .mapToObj(id -> getSingleBoardDTO(id, invoicesCount))
                .collect(Collectors.toList());
    }

    public static Vendor getSingleVendor(Long id){
        return Vendor.builder()
                .id(id)
                .firstName("First Name " + id)
                .lastName("Last Name " + id)
                .build();
    }

    public static List<Vendor> getVendorList(Long vendorsCount){
        return LongStream.rangeClosed(1, vendorsCount)
                .mapToObj(id -> getSingleVendor(id))
                .collect(Collectors.toList());
    }

    public static VendorDTO getSingleVendorDTO(Long id){
        return VendorDTO.builder()
                .id(id)
                .firstName("First Name " + id)
                .lastName("Last Name " + id)
                .build();
    }

    public static List<VendorDTO> getVendorListDTO(Long vendorsCount){
        return LongStream.rangeClosed(1, vendorsCount)
                .mapToObj(id -> getSingleVendorDTO(id))
                .collect(Collectors.toList());
    }
}
