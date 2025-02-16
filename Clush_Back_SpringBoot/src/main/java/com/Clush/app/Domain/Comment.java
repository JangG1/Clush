package com.Clush.app.Domain;

import java.sql.Timestamp;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    // Board와의 ManyToOne 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    @JsonBackReference  // 순환 참조 방지
    private Board board;

    // 생성자 (빌더 패턴)
    private Comment(CommentBuilder builder) {
        this.commentNo = builder.commentNo;
        this.nickname = builder.nickname;
        this.content = builder.content;
        this.board = builder.board;
    }

    // 빌더 클래스
    public static class CommentBuilder {
        private int commentNo;
        private String nickname;
        private String content;
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

        public CommentBuilder board(Board board) {
            this.board = board;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }
}
