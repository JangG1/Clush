import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Board.css";
import { Link } from "react-router-dom";

function Board() {
  const [boardData, setBoardData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  // 게시판 모든 내용 조회
  useEffect(() => {
    const EX_IP = process.env.REACT_APP_EX_IP || "https://clush.shop:7777";

    setIsLoading(true); // 데이터 요청 시작 시 로딩 상태 활성화
    axios
      .get(EX_IP + "/clushAPI/getAllBoard")
      .then((response) => {
        setBoardData(response.data); // 받은 데이터로 상태 업데이트
      })
      .catch((error) => {
        console.error("Error fetching board data:", error);
      })
      .finally(() => {
        setIsLoading(false); // 요청 완료 후 로딩 상태 비활성화
      });
  }, []); // 빈 배열을 넣어서 처음 마운트될 때만 실행됨

  // 날짜 포맷 함수
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date
      .toLocaleDateString("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      })
      .replaceAll(".", ""); // '2025-02-10'으로 형식 맞추기
  };

  return (
    <div>
      <div className="boardTop">
        <div className="boardTitleText">BOARD</div>

        <button className="toBoardContentBtn">
          <Link to="/BoardWrite">글 작성하기</Link>
        </button>
      </div>

      <div className="boardBody">
        <div className="boardBodyTitle">
          <div className="boardBodyTitle1">게시판 번호</div>
          <div className="boardBodyTitle2">작성자</div>
          <div className="boardBodyTitle3">제목</div>
          <div className="boardBodyTitle4">작성날짜</div>
        </div>

        {isLoading ? (
          <div className="boardLoading">
            <img src="/image/clush_logo2.png" className="LoadingImage" />
            <br></br>
            <p>게시글 불러오는 중...</p>
          </div>
        ) : boardData.length > 0 ? (
          boardData.map((item, index) => (
            <div key={index} className="boardItem">
              <div className="boardBody1">{item.boardNo}</div>
              <div className="boardBody2">{item.nickname}</div>
              {/* 게시글 제목을 클릭하면, boardNo를 URL 파라미터로 넘깁니다 */}
              <div className="boardBody3">
                <Link to={`/BoardContent/${item.boardNo}`}>{item.title}</Link>
              </div>
              <div className="boardBody4">{formatDate(item.boardDate)}</div>
            </div>
          ))
        ) : (
          <div className="boardTemp" style={styles.boardTemp}>
            게시글이 없습니다.
          </div>
        )}
      </div>
    </div>
  );
}

const styles = {
  boardTemp: {
    padding: "20px 0px 20px 0px",
    textAlign: "center",
  },
};

export default Board;
