package com.Clush.app.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Clush.app.Domain.Board;
import com.Clush.app.Repository.BoardRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    
    // 클래스 레벨에서 Logger 초기화
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Board> getAllBoards() {
        try {
            return boardRepository.findAll();
        } catch (Exception e) {
            log.error("데이터 조회 중 오류 발생", e);
            return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
        }
    }

}

