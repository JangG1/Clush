package com.Clush.app.Board;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.BoardDTO;
import com.Clush.app.Repository.BoardRepository;
import com.Clush.app.Service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardRepository boardRepository;
	private final BoardService boardService;

    // 클래스 레벨에서 Logger 초기화
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    
	
 // 모든 게시판 조회
    @Operation(summary = "Get all Board Items")
    @GetMapping("/getAllBoard")
    public List<BoardDTO> getAllBoard() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardDTO::new).collect(Collectors.toList());
    }

	// 게시판 번호를 통한 조회
    @Operation(summary = "Get Board by BoardNo")
	@GetMapping("/getBoard/{boardNo}")
	public ResponseEntity<Board> getBoardDetails(@PathVariable("boardNo") int boardNo) {
		Board board = boardRepository.findById(boardNo).orElse(null);
		if (board == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(board);
	}

	// 게시판 정보 저장
    @Operation(summary = "Save Board")
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
	
	// 게시판 정보 수정
    @Operation(summary = "Update Board by BoardNo")
    @PutMapping("/updateBoard/{boardNo}")
    public ResponseEntity<BoardDTO> updateBoard(
        @PathVariable int boardNo,
        @RequestBody BoardDTO boardDTO
    ) {
        BoardDTO updatedBoard = boardService.updateBoard(boardNo, boardDTO);
        return ResponseEntity.ok(updatedBoard);
    }
    
	// 게시판 번호를 통한 조회 기준으로 게시판 삭제
    @Operation(summary = "Delete Board by BoardNo")
	@DeleteMapping("/removeBoard/{boardNo}")
	public void deleteBoard(@PathVariable("boardNo") Board boardNo) {
		boardRepository.delete(boardNo);
	}
}