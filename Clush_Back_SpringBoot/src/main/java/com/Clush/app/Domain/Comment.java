package com.Clush.app.Domain;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonBackReference;

//파이프라인에서 어노테이션 관련 에러가 많이 발생(추후 디버깅 예정, 우선은 대부분 수동으로 구현)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
