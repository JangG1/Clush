package com.Clush.app.Domain;

import lombok.Getter;
import lombok.Setter;

public class BoardDTO {
    private String nickname;
    private String title;
    private String content;

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
