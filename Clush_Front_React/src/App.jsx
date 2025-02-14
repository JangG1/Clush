import React, { useState } from "react";
import "./App.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./components/Home";
import ToDo from "./components/ToDo";
import Calendar from "./components/Calendar";
import Board from "./components/Board";
import BoardWrite from "./components/BoardWrite";
import BoardContent from "./components/BoardContent";
import About from "./components/About";

function App() {
  const [menuBarHover, setIsHovering] = useState(false);

  //페이지 최상단 이동
  const scrollToTop = (e) => {
    e.preventDefault(); // 기본 동작인 페이지 리로드를 방지합니다.
    window.scrollTo({
      top: 0,
      behavior: "smooth", // 부드럽게 스크롤
    });
  };

  return (
    <div className="App">
      {/* Header 시작 */}
      <header className="App-header">
        {/* 라우팅 메뉴바 시작*/}
        <Router>
          <div
            style={menuBarHover ? styles.menuBarHover : styles.menu}
            onMouseOver={() => setIsHovering(true)}
            onMouseOut={() => setIsHovering(false)}
          >
            <a href="/">
              <img src="/image/clush_logo1.png" style={styles.clushLogo} />
            </a>
            <nav style={menuBarHover ? styles.nav : styles.navHide}>
              <Link style={styles.navBoardBtn} to="/ToDo">
                ToDo
              </Link>
              <Link style={styles.navBoardBtn} to="/Calendar">
                Calendar
              </Link>
              <Link style={styles.navBoardBtn} to="/Board">
                Board
              </Link>
              <Link style={styles.navBoardBtn} to="/About">
                About
              </Link>
            </nav>
          </div>

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/ToDo" element={<ToDo />} />
            <Route path="/Calendar" element={<Calendar />} />
            <Route path="/Board" element={<Board />} />
            <Route path="/BoardWrite" element={<BoardWrite />} />
            <Route path="/BoardContent/:boardNo" element={<BoardContent />} />
            <Route path="/About" element={<About />} />
          </Routes>
        </Router>
      </header>

      {/* 라우팅 메뉴바 종료 */}
      {/* Header 종료 */}

      <br></br>

      <br></br>

      {/* Footer 시작 */}
      <footer>
        <div style={styles.footer}>
          <div>
            <img src="/image/clush_logo1.png" style={styles.clushLogo} />
            <a className="focus" href="/" onClick={scrollToTop}>
              <img
                src="/image/topBtn.png"
                style={styles.topBtn}
                alt="Top Button"
              />
            </a>
          </div>
          <br></br>
          <div>개인정보처리방침 | 이메일무단수집거부</div>
          <br></br>
          <div>
            (주)클러쉬 | 서울특별시 강남구 테헤란로20길 9 5층 (동궁빌딩) |
            대표번호 02.2039.0100 | contact@clush.net
          </div>
          <br></br>
          <div>COPYRIGHT(C) CLUSH INC. ALL RIGHTS RESERVED.</div>
        </div>
      </footer>

      {/* Footer 종료 */}
    </div>
  );
}

const styles = {
  clushLogo: {
    backgroundColor: "rgba(0, 0, 0, 0)", // 배경만 투명
    height: "35px",
    width: "140px",
    padding: "10px",
  },
  menu: {
    backgroundColor: "rgba(0, 0, 0, 0)", // 배경만 투명
    color: "white",
    position: "fixed", // 메뉴바 고정
    top: "0",
    left: "0",
    zIndex: "1000",
    width: "100%",
    padding: "10px",
    display: "flex", // 메뉴 아이템을 가로로 배치
    alignItems: "center", // 세로 가운데 정렬
  },
  menuBarHover: {
    backgroundColor: "rgba(42, 54, 107, 0.3)", // 배경만 투명
    color: "white",
    position: "fixed", // 메뉴바 고정
    top: "0",
    left: "0",
    zIndex: "1000",
    width: "100%",
    padding: "10px",
    display: "flex", // 메뉴 아이템을 가로로 배치
    alignItems: "center", // 세로 가운데 정렬
  },
  nav: {
    fontfamily: "NanumBarunGothic",
    marginLeft: "40%",
    transform: "translateY(-10%)",
    transition: "transform 1s ease", // 부드러운 이동 효과
  },
  navHide: {
    fontfamily: "NanumBarunGothic",
    marginLeft: "40%",
    transform: "translateY(-200%)",
    transition: "transform 1s ease", // 부드러운 이동 효과
  },
  navBoardBtn: {
    fontWeight: "900",
    fontfamily: "NanumBarunGothic",
    color: "white",
    fontSize: "22px",
    paddingRight: "20%",
    textDecoration: "none" /* 마우스 올려도 밑줄 제거 */,
  },

  footer: {
    height: "200px",
    backgroundColor: "rgba(62, 58, 122, 0.3)", // 배경만 투명
    color: "#333",
    padding: "50px 0px 0px 40px",
    fontSize: "12px",
  },
  topBtn: {
    float: "right",
    width: "40px",
    height: "40px",
    marginRight: "3%",
  },
};

export default App;
