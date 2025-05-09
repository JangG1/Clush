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
import com.Clush.app.Domain.Comment;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest  // 모든 빈을 로드
@AutoConfigureMockMvc  // MockMvc 활성화
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    // MockMvc를 사용하여 실제 HTTP 요청 없이 컨트롤러의 엔드포인트를 테스트.
    // mockMvc.perform(요청).andExpect(검증) 형식으로 API 테스트 진행.

    @MockBean // Mocking하여 실제 로직을 실행하지 않도록 설정.
    private BoardService boardService;

    @MockBean // Mocking하여 실제 로직을 실행하지 않도록 설정.
    private BoardRepository boardRepository;

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환용

    private Board board;
    private BoardDTO boardDTO;

    @BeforeEach
    void setUp() {
    	List<Comment> comments = new ArrayList<>();  // 댓글 리스트 초기화
    	board = new Board();
    	board.setBoardNo(1);
    	board.setNickname("testNickname");
    	board.setTitle("testTitle");
    	board.setContent("testContent");
    	board.setComments(new ArrayList<>());
        boardDTO = new BoardDTO(board);
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
        Board board = new Board();
        board.setBoardNo(1);
        board.setNickname("testNickname");
        board.setTitle("testTitle");
        board.setContent("testContent");

        given(boardRepository.findById(1)).willReturn(Optional.of(board));  // 존재하는 데이터 반환

        System.out.println("findById(1) 결과: " + boardRepository.findById(1)); // 확인용 로그

        doNothing().when(boardRepository).deleteById(1);

        mockMvc.perform(delete("/removeBoard/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("게시판 삭제 완료"));
    }

}
