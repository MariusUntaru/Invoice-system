package com.invoice.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.invoice.system.controller.dto.BoardDTO;
import com.invoice.system.entity.Board;

@Mapper
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    BoardDTO boardToDto(Board board);
    Board dtoToBoard(BoardDTO boardDTO);
}
