package com.invoice.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.invoice.system.controller.dto.VendorDTO;
import com.invoice.system.entity.Vendor;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToDto(Vendor vendor);
    Vendor dtoToVendor(VendorDTO vendorDTO);
}
