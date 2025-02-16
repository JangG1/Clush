package com.Clush.app.Domain;

import java.util.List;
import java.util.stream.Collectors;

public class BoardDTO {
    private String nickname;
    private String title;
    private String content;
    private List<CommentDTO> comments;

    // 기본 생성자
    public BoardDTO() {}

    // 변환용 생성자
    public BoardDTO(Board board) {
        this.nickname = board.getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.comments = board.getComments().stream()
                            .map(CommentDTO::new)
                            .collect(Collectors.toList());
    }

    // Getter & Setter
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    // BoardDTO → Board 변환 메서드
    public Board toEntity() {
        return new Board.BoardBuilder()
                .nickname(this.nickname)
                .title(this.title)
                .content(this.content)
                .build();
    }

    // toString() 오버라이드
    @Override
    public String toString() {
        return "BoardDTO{" +
                "nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                '}';
    }
}
