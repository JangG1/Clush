//package com.Clush.app.Service;
//
//import com.Clush.app.Domain.Comment;
//import com.Clush.app.Domain.Board;
//import com.Clush.app.Repository.CommentRepository;
//import com.Clush.app.Repository.BoardRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CommentService {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    public Comment saveComment(int boardNo, String nickname, String content) {
//        // 게시물 조회
//        Board board = boardRepository.findById(boardNo)
//                .orElseThrow(() -> new RuntimeException("Board not found"));
//
//        // 댓글 객체 생성
//        Comment comment = new Comment();
//        comment.setBoard(board);
//        comment.setNickname(nickname);
//        comment.setContent(content);
//
//        // 댓글 저장
//        return commentRepository.save(comment);
//    }
//}
