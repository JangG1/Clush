package com.Clush.app.Domain;

import lombok.Data;

@Data
public class CommentRequest {
    private int boardNo;
    private String nickname;
    private String content;
}


