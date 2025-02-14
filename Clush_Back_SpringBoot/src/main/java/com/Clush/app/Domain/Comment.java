package com.Clush.app.Domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    private int commentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")  // 외래키로 연결
    @JsonIgnore  // 무한 참조 방지
    private Board board;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 1000)
    private String content;

    @CreationTimestamp
    private Timestamp comment_date;

 // JSON에서 "board": "1"로 들어오는 값을 Board 객체로 변환
    public void setBoard(Long boardNo) {
        Board board = new Board(); // 새 Board 객체 생성
        board.setBoardNo(boardNo.intValue()); // int로 변환 후 설정
        this.board = board;
    }
    
    
    // 새롭게 추가할 Board 객체를 받는 setter
    public void setBoard(Board board) {
        this.board = board;
    }
}

