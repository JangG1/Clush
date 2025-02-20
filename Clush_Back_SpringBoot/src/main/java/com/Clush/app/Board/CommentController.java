package com.Clush.app.Board;

import lombok.RequiredArgsConstructor;

import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.Comment;
import com.Clush.app.Domain.CommentRequest;
import com.Clush.app.Repository.BoardRepository;
import com.Clush.app.Repository.CommentRepository;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

   private final  BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    
    @Operation(summary = "Get Comment By BoardNo")
	@GetMapping("/getComments/{boardNo}")
	public ResponseEntity<List<Comment>> getComments(@PathVariable int boardNo) {
        List<Comment> comments = commentRepository.findByBoardBoardNo(boardNo);
        return ResponseEntity.ok(comments);
    }
	
    // 댓글 작성 API
    @Operation(summary = "Save Comment")
	@PostMapping("/addComment")
	public ResponseEntity<String> createComment(@RequestBody Map<String, Object> requestData) {
	    int boardNo = (int) requestData.get("boardNo"); // boardNo 숫자로 받음
	    String nickname = (String) requestData.get("nickname");
	    String content = (String) requestData.get("content");

	    Board board = boardRepository.findById(boardNo)
	        .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

	    Comment comment = Comment.builder()
	        .board(board) // Board 객체 설정
	        .nickname(nickname)
	        .content(content)
	        .build();

	    commentRepository.save(comment);
	    return ResponseEntity.ok("댓글이 저장되었습니다.");
	}



}
