import React, { useState, useEffect } from "react";
import { useStore } from "../store/Store"; // Zustand 스토어 import
import "./ToDo.css";
import axios from "axios";

const TodoApp = () => {
  const { todos, addTodo, deleteTodo } = useStore();
  const [input, setInput] = useState("");
  const [news, setNews] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const fetchNews = async () => {
    if (!keyword) return;

    setIsLoading(true); // 데이터 요청 시작 시 로딩 상태 활성화
    const EX_IP = process.env.REACT_APP_EX_IP || "http://clush.shop:7777";
    axios
      .get(EX_IP + `/clushAPI/news/${encodeURIComponent(keyword)}`)
      .then((response) => {
        console.log("데이터 도착 : ", response.data.data);
        setNews(response.data.data.items);
      })
      .catch((error) => {
        console.error("Error fetching board details:", error);
      })
      .finally(() => {
        setIsLoading(false); // 요청 완료 후 로딩 상태 비활성화
      });
  };

  // 할 일을 추가하는 함수
  const handleAddTodo = () => {
    if (input.trim()) {
      const currentTime = new Date().toLocaleTimeString(); // 현재 시간을 "HH:MM:SS" 형식으로 저장
      addTodo({ id: Date.now(), text: input, time: currentTime });
      setInput("");
    }
  };

  useEffect(() => {
    console.log(news);
  }, [news]);

  function cleanChar(title) {
    if (!title) return "";
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
                  addTodo({ id: Date.now(), text: input });
                  setInput("");
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
            />
            <button className="newsSearchBtn" onClick={fetchNews}>
              🔍
            </button>
          </div>

          {/* ✅ 로딩 중 화면 표시 */}
          {isLoading ? (
            <div className="newsLoading">
              <img src="/image/clush_logo2.png" className="LoadingImage" />
              <br></br>
              <p>뉴스를 불러오는 중...</p>
            </div>
          ) : news.length > 0 ? (
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
              <div className="newsCellBodyTempImage">
                <img src="/image/newsBodyTemp.gif" />
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TodoApp;
