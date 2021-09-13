package com.invoice.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.invoice.system.entity.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
}
