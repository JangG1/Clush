package com.Clush.app.Domain;

import java.sql.Timestamp;

public class CommentDTO {

    private int commentNo;
    private String nickname;
    private String content;
    private Timestamp commentDate;

    // 기본 생성자
    public CommentDTO() {}

    // Entity -> DTO 변환용 생성자
    public CommentDTO(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.commentDate = comment.getCommentDate();
    }

    // Getter & Setter 메서드
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

    // DTO -> Entity 변환 메서드
    public Comment toEntity() {
        return new Comment(this.commentNo, this.nickname, this.content, this.commentDate, null); // Board는 null, 필요시 세팅
    }
}
