package com.Clush.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Clush.app.Domain.Board;
import com.Clush.app.Repository.BoardRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
}

