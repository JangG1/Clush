package com.Clush.app.TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.Comment;
import com.Clush.app.Repository.BoardRepository;
import com.Clush.app.Repository.CommentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CommentRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CommentRepositoryTest.class);

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CommentRepository commentRepository;

    private Board mockBoard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        log.info("🔧 테스트 초기화 중...");
        mockBoard = new Board();
        mockBoard.setBoardNo(1);
        mockBoard.setNickname("testNickname");
        mockBoard.setTitle("testTitle");
        mockBoard.setContent("testContent");
        mockBoard.setComments(Arrays.asList());
    }

    @Test
    @DisplayName("댓글 저장 테스트 - 정상 케이스")
    void testSaveComment_Success() {
        log.info("🚀 댓글 저장 테스트 시작");
        
        Comment comment = new Comment();
        comment.setNickname("testUser");
        comment.setContent("This is a test comment");
        comment.setBoard(mockBoard);

        when(boardRepository.findById(1)).thenReturn(Optional.of(mockBoard));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        log.info("🔍 게시판 ID 1 조회 요청");
        Optional<Board> boardOpt = boardRepository.findById(1);
        assertTrue(boardOpt.isPresent(), "게시글이 존재해야 합니다.");
        
        log.info("💾 댓글 저장 요청: {}", comment);
        Comment savedComment = commentRepository.save(comment);

        assertNotNull(savedComment);
        assertEquals("testUser", savedComment.getNickname());
        assertEquals("This is a test comment", savedComment.getContent());

        log.info("✅ 댓글 저장 테스트 성공");
        verify(boardRepository, times(1)).findById(1);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 저장 테스트 - 게시글이 존재하지 않는 경우")
    void testSaveComment_BoardNotFound() {
        log.info("🚀 댓글 저장 테스트 (게시글 없음) 시작");
        
        when(boardRepository.findById(999)).thenReturn(Optional.empty());

        log.info("🔍 존재하지 않는 게시글 ID 999 조회 요청");
        Optional<Board> boardOpt = boardRepository.findById(999);
        assertFalse(boardOpt.isPresent(), "게시글이 존재하지 않아야 합니다.");

        log.warn("❌ 게시글이 존재하지 않아 댓글 저장 안 됨");
        verify(boardRepository, times(1)).findById(999);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("게시글 번호로 댓글 조회 테스트")
    void testFindCommentsByBoardNo() {
        log.info("🚀 게시글 번호로 댓글 조회 테스트 시작");
        
        Comment comment1 = new Comment();
        comment1.setCommentNo(1);
        comment1.setNickname("user1");
        comment1.setContent("Hello");

        Comment comment2 = new Comment();
        comment2.setCommentNo(2);
        comment2.setNickname("user2");
        comment2.setContent("World");

        List<Comment> mockComments = Arrays.asList(comment1, comment2);
        when(commentRepository.findByBoardBoardNo(1)).thenReturn(mockComments);

        log.info("🔍 게시글 ID 1의 댓글 조회 요청");
        List<Comment> retrievedComments = commentRepository.findByBoardBoardNo(1);

        assertEquals(2, retrievedComments.size());
        assertEquals("user1", retrievedComments.get(0).getNickname());
        assertEquals("user2", retrievedComments.get(1).getNickname());

        log.info("✅ 게시글 ID 1의 댓글 조회 성공: {}개 댓글", retrievedComments.size());
        verify(commentRepository, times(1)).findByBoardBoardNo(1);
    }
}
