import React, { useState } from "react";
import { useStore } from "../store/Store"; // Zustand 스토어 import
import "./ToDo.css";
import axios from "axios";

const TodoApp = () => {
  const { todos, addTodo, deleteTodo } = useStore();
  const [input, setInput] = useState("");
  const [news, setNews] = useState([]); // 초기값을 빈 배열로 설정
  const [keyword, setKeyword] = useState(""); // 검색어

  const fetchNews = async () => {
    if (!keyword) return; // 검색어가 비어 있으면 요청을 보내지 않음

    axios
      .get(`/api/news/${encodeURIComponent(keyword)}`) // 해당 게시물의 ID로 API 호출
      .then((response) => {
        console.log("데이터 도착 : ", response.data); // 받은 데이터를 확인
        setNews(response.data.data.items); // JSON에서 필요한 데이터만 추출하여 상태에 설정
      })
      .catch((error) => {
        console.error("Error fetching board details:", error);
      });

    console.log(news); // news의 실제 데이터 확인
  };

  // 할 일을 추가하는 함수
  const handleAddTodo = () => {
    if (input.trim()) {
      const currentTime = new Date().toLocaleTimeString(); // 현재 시간을 "HH:MM:SS" 형식으로 저장
      addTodo({ id: Date.now(), text: input, time: currentTime });
      setInput("");
    }
  };

  function cleanChar(title) {
    return title
      .replace(/&quot;/g, '"')
      .replace(/<br\s*\/?>/g, " ")
      .replace(/<b>/g, " ")
      .replace(/<\/b>/g, " ");
  }

  return (
    <div>
      <img src="/image/clush_logo2.png" className="ToDoClushLogo" />

      <div className="ToDo">
        <div className="ToDoBody">
          <div className="todoInput">
            <input
              type="text"
              className="todoInputBar"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              placeholder="할 일을 입력하세요"
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  handleAddTodo();
                }
              }}
            />
            <button className="todoInputBtn" onClick={handleAddTodo}>
              추가
            </button>
          </div>

          <div className="todoList">
            <div className="todoListTitle">TO-DO LIST</div>
            {todos.map((todo) => (
              <div className="todoIdx" key={todo.id}>
                <div className="todoTime">{todo.time}</div>
                <div className="todoText">{todo.text}</div>

                <div className="todoDelete">
                  <button
                    className="todoDeleteBtn"
                    onClick={() => deleteTodo(todo.id)}
                  >
                    X
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="newsBody">
          {/* 검색어 입력 */}
          <div className="newsSearchBar">
            <img src="/image/clush_logo2.png" />
            <input
              type="text"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              className="newsSearchInputBar"
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  fetchNews();
                }
              }}
              tabIndex={0} // 키보드 포커스를 받을 수 있도록 설정
            />

            <div type="button" className="newsSearchBtn" onClick={fetchNews}>
              🔍
            </div>
          </div>
          {news.length > 0 ? (
            <div className="newsCellBody">
              {news.map((item, index) => (
                <div key={index} className="newsCell">
                  <h3 className="newsCellTitle">{cleanChar(item.title)}</h3>
                  <h4 className="newsCellDesc">
                    {cleanChar(item.description)}
                  </h4>
                  <a
                    href={item.originallink}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="newsCellNewsLink"
                  >
                    원본 기사 보기
                  </a>
                  <p>{new Date(item.pubDate).toLocaleString()}</p>
                </div>
              ))}
            </div>
          ) : (
            <div className="newsCellBodyTemp">
              <div className="newsCellBodyTempText">무엇이든 검색해보세요!</div>
              <br />
              <img
                src="/image/newsBodyTemp.gif"
                className="newsCellBodyTempImage"
              />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TodoApp;
