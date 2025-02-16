package com.Clush.app.Domain;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CommentDTO {
    private int commentNo;
    private String nickname;
    private String content;
    private Timestamp commentDate;

    public CommentDTO(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.commentDate = comment.getCommentDate();
    }
}
