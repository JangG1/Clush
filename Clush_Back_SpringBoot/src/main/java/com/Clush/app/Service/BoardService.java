package com.Clush.app.Service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.BoardDTO;
import com.Clush.app.Repository.BoardRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    
    // 클래스 레벨에서 Logger 초기화
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    
    @Transactional(readOnly = true)
    public List<Board> getAllBoards() {
        try {
            List<Board> boards = boardRepository.findAll();
            for (Board board : boards) {
                if (board.getComments() == null) {
                    board.setComments(new ArrayList<>()); // Null 방지
                }
            }
            return boards;
        } catch (Exception e) {
            log.error("데이터 조회 중 오류 발생", e);
            return Collections.emptyList();
        }
    }

    // 게시글 수정 기능
    @Transactional
    public BoardDTO updateBoard(int boardNo, BoardDTO boardDTO) {
        // 1. 게시글 찾기
        Board board = boardRepository.findById(boardNo)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID: " + boardNo));

        // 2. 내용 업데이트
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setNickname(boardDTO.getNickname());

        // 3. 변경된 엔티티 저장 후 DTO로 변환하여 반환
        Board updatedBoard = boardRepository.save(board);
        return new BoardDTO(updatedBoard);
    }
}

