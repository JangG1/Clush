package com.Clush.app.Board;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.BoardDTO;
import com.Clush.app.Repository.BoardRepository;
import com.Clush.app.Service.BoardService;

import lombok.RequiredArgsConstructor;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
//@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	private final BoardRepository boardRepository;
	@Autowired
	private final BoardService boardService;

    // 클래스 레벨에서 Logger 초기화
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    
    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }
	
	// 모든 게시판 조회
    @GetMapping("/clushAPI/getAllBoard")
    public ResponseEntity<List<Board>> getAllBoard() {
        try {
            List<Board> boards = boardService.getAllBoards();
            return ResponseEntity.ok(boards); // 정상 처리 시, List<Board>를 반환
        } catch (Exception e) {
            log.error("보드 데이터 로딩 오류: ", e);
            Board errorBoard = new Board(); // 기본적인 error board 객체 생성
            errorBoard.setTitle("서버에서 오류가 발생했습니다.");
            errorBoard.setContent("오류 메시지: " + e.getMessage()); 
            List<Board> errorList = new ArrayList<>();
            errorList.add(errorBoard); // 오류 정보를 포함한 Board 객체를 리스트에 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorList); // 오류 정보를 포함한 리스트 반환
        }
    }


	// 게시판 번호를 통한 조회
	@GetMapping("/getBoard/{boardNo}")
	public ResponseEntity<Board> getBoardDetails(@PathVariable("boardNo") int boardNo) {
		Board board = boardRepository.findById(boardNo).orElse(null);
		if (board == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(board);
	}

	// 게시판 정보 저장
	@PostMapping("/boardSave")
	public ResponseEntity<String> addBoard(@RequestBody BoardDTO boardDTO) {
	    Board board = Board.builder()
	            .nickname(boardDTO.getNickname())
	            .title(boardDTO.getTitle())
	            .content(boardDTO.getContent())
	            .build();

	    boardRepository.save(board);
	    return ResponseEntity.status(HttpStatus.CREATED).body("게시판 등록 완료");
	}
	
	// 게시판 번호를 통한 조회 기준으로 게시판 삭제
	@DeleteMapping("/removeBoard/{boardNo}")
	public void deleteBoard(@PathVariable("boardNo") Board boardNo) {
		boardRepository.delete(boardNo);
	}
}