package com.invoice.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.invoice.system.entity.Vendor;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
