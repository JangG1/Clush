package com.Clush.app.Board;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.BoardDTO;
import com.Clush.app.Repository.BoardRepository;
import com.Clush.app.Service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    
    // 모든 게시판 조회
    @Operation(summary = "Get all Board Items")
    @GetMapping("/getAllBoard")
    public List<BoardDTO> getAllBoard() {
        List<Board> boards = boardRepository.findAll();
        log.info("📌 모든 게시판 가져오기: {}", boards);
        return boards.stream().map(BoardDTO::new).collect(Collectors.toList());
    }

    // 게시판 번호를 통한 조회
    @Operation(summary = "Get Board by BoardNo")
    @GetMapping("/getBoard/{boardNo}")
    public ResponseEntity<Board> getBoardDetails(@PathVariable("boardNo") int boardNo) {
        log.info("🔍 게시판 조회 요청: boardNo = {}", boardNo);
        Board board = boardRepository.findById(boardNo).orElse(null);
        
        if (board == null) {
            log.warn("⚠️ 게시판이 존재하지 않음: boardNo = {}", boardNo);
            return ResponseEntity.notFound().build();
        }
        
        log.info("✅ 게시판 조회 성공: {}", board);
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
        
        log.info("📝 게시판 저장 요청: {}", board);
        boardRepository.save(board);
        log.info("✅ 게시판 저장 완료: boardNo = {}", board.getBoardNo());
        
        return ResponseEntity.status(HttpStatus.CREATED).body("게시판 등록 완료");
    }
    
    // 게시판 정보 수정
    @Operation(summary = "Update Board by BoardNo")
    @PutMapping("/updateBoard/{boardNo}")
    public ResponseEntity<BoardDTO> updateBoard(
        @PathVariable int boardNo,
        @RequestBody BoardDTO boardDTO
    ) {
        log.info("✏️ 게시판 수정 요청: boardNo = {}, 수정 내용 = {}", boardNo, boardDTO);
        BoardDTO updatedBoard = boardService.updateBoard(boardNo, boardDTO);
        log.info("✅ 게시판 수정 완료: {}", updatedBoard);
        return ResponseEntity.ok(updatedBoard);
    }
    
    // 게시판 삭제
    @Operation(summary = "Delete Board by BoardNo")
    @DeleteMapping("/removeBoard/{boardNo}")
    public ResponseEntity<String> deleteBoard(@PathVariable int boardNo) {
        log.info("🔥 삭제 요청이 컨트롤러에 도착함! boardNo = {}", boardNo);

        Optional<Board> board = boardRepository.findById(boardNo);
        if (board.isEmpty()) {
            log.warn("❌ 해당 게시판이 존재하지 않음: boardNo = {}", boardNo);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판이 존재하지 않습니다.");
        }

        boardRepository.deleteById(boardNo);
        log.info("✅ 게시판 삭제 완료! boardNo = {}", boardNo);

        return ResponseEntity.ok("게시판 삭제 완료");
    }
}
