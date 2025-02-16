package com.Clush.app.Domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)  // JSON에 없는 필드는 무시
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    // 수동으로 builder 메서드 추가 (Optional)
    public static BoardBuilder builderWithBoardNo(int boardNo) {
        return builder().boardNo(boardNo);  // builder로 생성하며 boardNo만 설정
    }
}
