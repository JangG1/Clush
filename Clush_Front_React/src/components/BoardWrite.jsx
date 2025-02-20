import React, { useState } from "react";
import axios from "axios";
import "./BoardWrite.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function BoardWrite() {
  const [message1, setMessage1] = useState("");
  const [message2, setMessage2] = useState("");
  const [message3, setMessage3] = useState("");

  const handleSendMessage = () => {
    const EX_IP = process.env.REACT_APP_EX_IP || "https://clush.shop:7777";

    axios
      .post(EX_IP + `/clushAPI/boardSave`, {
        nickname: message1,
        title: message2,
        content: message3,
      })
      .then((response) => {
        alert("게시물이 작성되었습니다.");
        window.location.href = "/Board";
      })
      .catch((error) => {
        console.error("Error sending message:", error);
      });
  };

  return (
    <div>
      <div className="boardTop"></div>
      <img src="/image/clush_logo2.png" className="boardWriteClushLogo" />
      <div>
        <button className="toBoardBtnByBW">
          <Link to="/Board">뒤로가기</Link>
        </button>
      </div>

      <div className="boardWriteBody">
        <div className="boardWriteBodyTitle1">
          <div>작성자</div>
          <input
            className="boardWriteBody1"
            type="text"
            value={message1}
            onChange={(e) => setMessage1(e.target.value)}
          />
        </div>
        <div className="boardWriteBodyTitle2">
          <div>제목</div>
          <input
            className="boardWriteBody2"
            type="text"
            value={message2}
            onChange={(e) => setMessage2(e.target.value)}
          />
        </div>
        <div className="boardWriteBodyTitle3">
          <div>내용</div>
          <textarea
            className="boardWriteBody3"
            type="text"
            value={message3}
            onChange={(e) => setMessage3(e.target.value)}
          />
        </div>
      </div>

      <button className="sendBoard" onClick={handleSendMessage}>
        작성하기
      </button>
    </div>
  );
}

export default BoardWrite;
