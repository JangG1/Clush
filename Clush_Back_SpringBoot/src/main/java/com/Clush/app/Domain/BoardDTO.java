package com.Clush.app.Domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDTO {

    private int boardNo;
    private String nickname;
    private String title;
    private String content;
    private Timestamp boardDate;
    private List<CommentDTO> comments;

    // 기본 생성자
    public BoardDTO() {}

    // Entity -> DTO 변환용 생성자
    public BoardDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.nickname = board.getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardDate = board.getBoard_date();
        // 댓글 리스트를 CommentDTO로 변환하여 설정
        this.comments = board.getComments().stream()
                              .map(comment -> new CommentDTO(comment))
                              .collect(Collectors.toList());
    }

    // Getter & Setter 메서드
    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

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

    public Timestamp getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(Timestamp boardDate) {
        this.boardDate = boardDate;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    // DTO -> Entity 변환 메서드
    public Board toEntity() {
        // 댓글 리스트를 Entity로 변환
        List<Comment> commentEntities = this.comments.stream()
                                                     .map(CommentDTO::toEntity)
                                                     .collect(Collectors.toList());
        // BoardEntity로 변환 후 반환
        return Board.builder()
                    .boardNo(this.boardNo)
                    .nickname(this.nickname)
                    .title(this.title)
                    .content(this.content)
                    .comments(commentEntities)
                    .build();
    }
}
