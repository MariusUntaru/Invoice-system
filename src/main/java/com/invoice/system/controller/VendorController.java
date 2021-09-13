package com.invoice.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.system.controller.dto.VendorDTO;
import com.invoice.system.service.VendorService;

@RestController
@RequestMapping("/vendors")
public class VendorController extends CrudController<VendorDTO> {

    public VendorController(VendorService vendorService) {
        super(vendorService);
    }
}
