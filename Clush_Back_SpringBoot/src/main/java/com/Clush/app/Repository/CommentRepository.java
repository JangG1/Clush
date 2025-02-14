package com.Clush.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Clush.app.Domain.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoardBoardNo(int boardNo);  // 특정 게시글의 댓글 조회
}
