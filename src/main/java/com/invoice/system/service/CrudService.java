package com.invoice.system.service;

import java.util.List;
import java.util.Optional;

import com.invoice.system.controller.dto.BaseDTO;

public interface CrudService<T extends BaseDTO> {

    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T dto);

    void delete(Long id);

    T update(Long id, T dto);
}
