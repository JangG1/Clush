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

import lombok.RequiredArgsConstructor;

import java.io.*;

@RestController
public class BoardController {

	private final BoardRepository boardRepository;

    // ✅ 생성자 주입 방식 (권장)
    @Autowired
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

	// 모든 게시판 조회
	@GetMapping("/getAllBoard")
	public List<Board> getAllBoard() {		
		return boardRepository.findAll();
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