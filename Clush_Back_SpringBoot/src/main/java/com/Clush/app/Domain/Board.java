package com.Clush.app.Domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)  // JSON에 없는 필드는 무시
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)  // Lombok이 기본 생성자를 자동 생성
@AllArgsConstructor
//@Builder
@DynamicInsert
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private int boardNo;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 100)
    private String title;

    @Column(length = 5000)
    private String content;

    @CreationTimestamp
    private Timestamp board_date;

    // 댓글 목록을 Board와 매핑 (양방향 관계 설정)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // 무한 참조 방지
    private List<Comment> comments = new ArrayList<>();

    // 빌더 패턴을 사용한 생성자
    private Board(BoardBuilder builder) {
        this.boardNo = builder.boardNo;
        this.nickname = builder.nickname;
        this.title = builder.title;
        this.content = builder.content;
    }

    // 빌더 클래스
    public static class BoardBuilder {
        private int boardNo;
        private String nickname;
        private String title;
        private String content;

        public BoardBuilder boardNo(int boardNo) {
            this.boardNo = boardNo;
            return this;
        }

        public BoardBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public BoardBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BoardBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    public static BoardBuilder builder() {
        return new BoardBuilder();
    }
}


