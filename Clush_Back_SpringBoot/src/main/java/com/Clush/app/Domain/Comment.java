package com.Clush.app.Domain;

import java.sql.Timestamp;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonBackReference;

//파이프라인에서 어노테이션 관련 에러가 많이 발생(추후 디버깅 예정, 우선은 대부분 수동으로 구현)
@Entity
@DynamicInsert
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private int commentNo;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 1000)
    private String content;

    @CreationTimestamp
    private Timestamp commentDate;

    // Board와의 ManyToOne 관계 설정 (양방향 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    @JsonBackReference // 순환 참조 방지
    private Board board;

    // 🔹 기본 생성자 (필수)
    public Comment() {
        this.nickname = "defaultNickname";
        this.content = "defaultContent";
        this.commentDate = new Timestamp(System.currentTimeMillis());
    }

    // 🔹 전체 필드를 포함하는 생성자
    public Comment(int commentNo, String nickname, String content, Timestamp commentDate, Board board) {
        this.commentNo = commentNo;
        this.nickname = nickname;
        this.content = content;
        this.commentDate = commentDate;
        this.board = board;
    }

    // 🔹 Getter & Setter 메서드 (Lombok 대신 수동 작성)
    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    // 🔹 빌더 패턴 구현 (Lombok 없이 수동 작성)
    public static class CommentBuilder {
        private int commentNo;
        private String nickname;
        private String content;
        private Timestamp commentDate;
        private Board board;

        public CommentBuilder commentNo(int commentNo) {
            this.commentNo = commentNo;
            return this;
        }

        public CommentBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public CommentBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CommentBuilder commentDate(Timestamp commentDate) {
            this.commentDate = commentDate;
            return this;
        }

        public CommentBuilder board(Board board) {
            this.board = board;
            return this;
        }

        public Comment build() {
            return new Comment(commentNo, nickname, content, commentDate, board);
        }
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }
}
