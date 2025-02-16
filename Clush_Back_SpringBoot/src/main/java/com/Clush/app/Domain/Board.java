package com.Clush.app.Domain;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.*; [2025/02/07] 해당 import로 에러 발생 (org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'controller' defined in file) 
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    public void setBoardNo(int boardNo) { //(Long도 받을 수 있도록 오버로딩 가능)
        this.boardNo = boardNo;
    }
}

