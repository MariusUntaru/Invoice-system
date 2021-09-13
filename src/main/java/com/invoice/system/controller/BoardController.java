package com.invoice.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.system.controller.dto.BoardDTO;
import com.invoice.system.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController extends CrudController<BoardDTO> {

    public BoardController(BoardService boardService) {
        super(boardService);
    }
}
