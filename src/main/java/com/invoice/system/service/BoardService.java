package com.invoice.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.invoice.system.controller.dto.BoardDTO;
import com.invoice.system.entity.Board;
import com.invoice.system.repository.BoardRepository;

import static com.invoice.system.mapper.BoardMapper.INSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class BoardService implements CrudService<BoardDTO> {

	@NotNull
    private final BoardRepository boardRepository = null;

    @Override
    public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardRepository.findAll().forEach(board -> boardDTOList.add(INSTANCE.boardToDto(board)));
        return boardDTOList;
    }

    @Override
    public Optional<BoardDTO> findById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        return boardOptional.map(INSTANCE::boardToDto);
    }

    @Override
    public BoardDTO save(BoardDTO boardDTO) {
        Board board = INSTANCE.dtoToBoard(boardDTO);
        return INSTANCE.boardToDto(boardRepository.save(board));
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO update(Long id, BoardDTO boardDTO) {
        Board savedBoard = boardRepository.findById(id).orElseThrow();
        Board boardToUpdate = INSTANCE.dtoToBoard(boardDTO);

        savedBoard.setTitle(boardToUpdate.getTitle());
        savedBoard.setNoticeList(boardToUpdate.getNoticeList());

        return INSTANCE.boardToDto(boardRepository.save(savedBoard));
    }
}
