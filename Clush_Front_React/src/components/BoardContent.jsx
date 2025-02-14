import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom"; // URL 파라미터 가져오기
import "./BoardContent.css";

import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function BoardContent() {
  const [boardDetails, setBoardDetails] = useState(null);
  const [replyData, setReplyData] = useState(""); // 댓글 상태
  const [nickname, setNickname] = useState(""); // 댓글 상태
  const [comments, setComments] = useState([]); // 댓글 목록 상태
  const { boardNo } = useParams(); // URL에서 boardNo 파라미터를 가져옵니다
  const [currentPage, setCurrentPage] = useState(1);
  const commentsPerPage = 5;

  // 게시물 상세 내용 조회
  useEffect(() => {
    axios
      .get(`/api/getBoard/${boardNo}`) // 해당 게시물의 ID로 API 호출
      .then((response) => {
        console.log(response.data); // 받은 데이터를 확인
        setBoardDetails(response.data); // 상태에 저장
      })
      .catch((error) => {
        console.error("Error fetching board details:", error);
      });

    // 댓글 조회
    axios
      .get(`/api/getComments/${boardNo}`) // 해당 게시물의 댓글 조회
      .then((response) => {
        console.log(response.data);
        setComments(response.data); // 댓글 목록 상태에 저장
      })
      .catch((error) => {
        console.error("Error fetching comments:", error);
      });
  }, [boardNo]); // boardNo가 변경될 때마다 호출됨

  if (!boardDetails) return <div className="loading">Loading...</div>; // 로딩 상태 표시

  // 게시물 삭제 함수
  const deleteBoard = () => {
    const pw = process.env.REACT_APP_ADMIN_PASSWORD;
    const password = prompt("비밀번호를 입력하세요:");
    console.log(password);

    if (boardDetails.nickname == "관리자") {
      console.log("현재 닉네임 : " + boardDetails.nickname);
      if (pw == password) {
        axios
          .delete(`/api/removeBoard/${boardNo}`) // 게시물 삭제 API 호출 (DELETE 요청)
          .then((response) => {
            console.log(response.data); // 응답 확인
            alert("게시물이 삭제되었습니다.");
            window.location.href = "/Board"; // 삭제 후 게시판 페이지로 이동
          })
          .catch((error) => {
            console.error("Error deleting board:", error);
          });
      } else {
        alert("패스워드가 일치하지 않습니다.");
      }
    }
  };

  // 댓글 작성 함수
  const handleAddComment = () => {
    if (replyData.trim() === "") {
      alert("댓글을 작성해주세요.");
      return;
    }
    axios
      .post(`/api/addComment`, {
        boardNo: Number(boardNo),
        nickname: nickname,
        content: replyData,
      })
      .then((response) => {
        console.log(response.data); // 응답 확인
        alert("댓글이 작성되었습니다.");
        setReplyData(""); // 댓글 작성 후 입력 필드 초기화
        setComments([...comments, response.data]); // 새 댓글을 기존 댓글 목록에 추가
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error adding comment:", error);
      });
  };

  // 날짜 포맷 함수
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return (
      date
        .toLocaleDateString("ko-KR", {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
        })
        .replaceAll(".", "") +
      "　" +
      date.toLocaleTimeString("ko-KR", {
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        hour12: false, // 24시간 형식
      })
    );
  };

  const indexOfLastComment = currentPage * commentsPerPage;
  const indexOfFirstComment = indexOfLastComment - commentsPerPage;
  const currentComments = comments.slice(
    indexOfFirstComment,
    indexOfLastComment
  );
  const totalPages = Math.ceil(comments.length / commentsPerPage);

  return (
    <div>
      <div className="boardContentContainer">
        <div className="boardContent">
          <h1 className="boardContentTitle">{boardDetails.title}</h1>
          <div className="boardContentMeta">
            <span className="boardContentNo">
              게시판 번호: {boardDetails.boardNo}
            </span>
            <span className="boardContentDate">
              작성 날짜: {formatDate(boardDetails.board_date)}
            </span>
            <span className="boardContentNickname">
              작성자: {boardDetails.nickname}
            </span>
          </div>
          <div className="boardContentBody">
            <p>{boardDetails.content}</p>
          </div>
        </div>
      </div>

      {/* 댓글 영역 */}
      <div className="reply">
        <div>댓글 {comments.length}개</div>

        {/* 댓글 목록 */}
        <div className="commentList">
          {currentComments.map((comment) => (
            <div key={comment.comment_no} className="commentItem">
              <div className="commentNickname">{comment.nickname}</div>
              <div className="commentContent">{comment.content}</div>
              <div className="commentDate">
                {formatDate(comment.comment_date)}
              </div>
            </div>
          ))}
        </div>

        {/* 페이지네이션 */}
        <div className="pagination">
          {Array.from({ length: totalPages }, (_, index) => (
            <button key={index + 1} onClick={() => setCurrentPage(index + 1)}>
              {index + 1}
            </button>
          ))}
        </div>
        {/* 댓글 입력 */}
        <div className="replyNickname">
          <span>작성자</span>
          <br />
          <input
            type="text"
            className="replyNicknameBody"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
        </div>
        <br />
        <span>내용</span>
        <br />
        <textarea
          className="replyBody"
          type="text"
          value={replyData}
          onChange={(e) => setReplyData(e.target.value)} // 댓글 내용 입력
        />
        <br />
        <button className="replyBtn" onClick={handleAddComment}>
          댓글달기
        </button>
      </div>

      {/* 게시물 삭제 및 뒤로가기 버튼 */}
      <div className="boardContentBtn">
        <button className="toBoardBtnByBC">
          <Link to="/Board">뒤로가기</Link>
        </button>

        <button className="boardContentDeleteBtn" onClick={deleteBoard}>
          삭제하기
        </button>
      </div>
    </div>
  );
}

export default BoardContent;
