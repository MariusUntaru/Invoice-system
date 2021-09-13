package com.invoice.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.invoice.system.controller.dto.VendorDTO;
import com.invoice.system.entity.Vendor;
import com.invoice.system.mapper.VendorMapper;
import com.invoice.system.repository.VendorRepository;

import static com.invoice.system.mapper.VendorMapper.INSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;


@Service
@RequiredArgsConstructor
public class VendorService implements CrudService<VendorDTO> {

	@NotNull
    private final VendorRepository vendorRepository = null;

    @Override
    public List<VendorDTO> findAll() {
        List<VendorDTO> vendorDTOList = new ArrayList<>();
        vendorRepository.findAll().forEach(vendor -> vendorDTOList.add(INSTANCE.vendorToDto(vendor)));
        return vendorDTOList;
    }

    @Override
    public Optional<VendorDTO> findById(Long id) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        return vendorOptional.map(VendorMapper.INSTANCE::vendorToDto);
    }

    @Override
    public VendorDTO save(VendorDTO vendorDTO) {
        Vendor vendor = INSTANCE.dtoToVendor(vendorDTO);
        return INSTANCE.vendorToDto(vendorRepository.save(vendor));
    }

    @Override
    public void delete(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDTO update(Long id, VendorDTO vendorDTO) {
        Vendor savedVendor = vendorRepository.findById(id).orElseThrow();
        Vendor vendorToUpdate = INSTANCE.dtoToVendor(vendorDTO);

        savedVendor.setFirstName(vendorToUpdate.getFirstName());
        savedVendor.setLastName(vendorToUpdate.getLastName());

        return INSTANCE.vendorToDto(vendorRepository.save(savedVendor));
    }
}
