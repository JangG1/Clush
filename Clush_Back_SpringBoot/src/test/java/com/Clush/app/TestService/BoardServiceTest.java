package com.Clush.app.TestService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Clush.app.Board.BoardController;
import com.Clush.app.Domain.Board;
import com.Clush.app.Domain.BoardDTO;
import com.Clush.app.Repository.BoardRepository;
import com.Clush.app.Service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환용

    private Board board;
    private BoardDTO boardDTO;

    @BeforeEach
    void setUp() {
        board = new Board(1, "user1", "title1", "content1", null, null);
        boardDTO = new BoardDTO(board);
    }

    @Test
    @DisplayName("모든 게시판 조회 테스트")
    void getAllBoard() throws Exception {
        List<BoardDTO> boardDTOList = Arrays.asList(boardDTO);

        given(boardRepository.findAll()).willReturn(Arrays.asList(board));

        mockMvc.perform(get("/getAllBoard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(boardDTOList.size()))
                .andExpect(jsonPath("$[0].nickname").value(boardDTO.getNickname()))
                .andExpect(jsonPath("$[0].title").value(boardDTO.getTitle()));
    }

    @Test
    @DisplayName("게시판 번호를 통한 조회 테스트")
    void getBoardDetails() throws Exception {
        given(boardRepository.findById(1)).willReturn(Optional.of(board));

        mockMvc.perform(get("/getBoard/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(board.getNickname()))
                .andExpect(jsonPath("$.title").value(board.getTitle()));
    }

    @Test
    @DisplayName("게시판 정보 저장 테스트")
    void addBoard() throws Exception {
        given(boardRepository.save(any(Board.class))).willReturn(board);

        mockMvc.perform(post("/boardSave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("게시판 등록 완료"));
    }

    @Test
    @DisplayName("게시판 정보 수정 테스트")
    void updateBoard() throws Exception {
        given(boardService.updateBoard(eq(1), any(BoardDTO.class))).willReturn(boardDTO);

        mockMvc.perform(put("/updateBoard/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(boardDTO.getNickname()))
                .andExpect(jsonPath("$.title").value(boardDTO.getTitle()));
    }

    @Test
    @DisplayName("게시판 삭제 테스트")
    void deleteBoard() throws Exception {
        doNothing().when(boardRepository).deleteByBoardNo(1);

        mockMvc.perform(delete("/removeBoard/1"))
                .andExpect(status().isOk());
    }
}
