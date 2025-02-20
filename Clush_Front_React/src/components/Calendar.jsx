import React, { useState, useEffect } from "react";
import { Calendar, Badge, Modal, Input, Button } from "antd";
import "./Calendar.css"; // ✅ CSS 파일 추가
import axios from "axios";
import dayjs from "dayjs";
import { data } from "react-router-dom";
const MyCalendar = () => {
  const [selectedDate, setSelectedDate] = useState(null); // 선택된 날짜
  const [schedule, setSchedule] = useState({}); // 각 날짜별 일정 관리
  const [newEvent, setNewEvent] = useState(""); // 새 일정 입력값
  const [weatherData, setWeatherData] = useState(null); // 날씨 데이터를 저장할 상태
  const [isWeatherModalVisible, setIsWeatherModalVisible] = useState(false); // 날씨 정보 모달 상태
  const [isDateAddModalVisible, setIsDateAddModalVisible] = useState(false); // 일정 추가 모달 상태
  const today = dayjs();
  const validDates = Array.from({ length: 5 }, (_, i) =>
    today.add(i + 1, "day").format("YYYY-MM-DD")
  );

  useEffect(() => {
    const EX_IP = process.env.REACT_APP_EX_IP || "https://clush.shop:7777";
    
    // 날씨 데이터를 API로부터 가져옴
    axios
      .get(`${EX_IP}/clushAPI/weather`) // 요청 URL 수정

      .then((response) => {
        setWeatherData(response.data); // 날씨 데이터 상태에 저장
      })
      .catch((error) => {
        console.error("날씨 데이터를 가져오는 데 실패했습니다:", error);
      });
  }, []);

  const weatherModal = (value) => {
    const dateFormat = value.format("YYYY-MM-DD");
    setSelectedDate(dateFormat); // 날짜 클릭 시 selectedDate 설정

    // 날짜를 설정한 후 날씨 모달을 바로 열도록 수정
    setIsWeatherModalVisible(true);
  };

  const dateAddModal = () => {
    setIsDateAddModalVisible(true); // 일정 추가 모달을 띄운다
  };

  const handleAddEvent = () => {
    if (newEvent.trim() && selectedDate) {
      const newSchedule = {
        ...schedule,
        [selectedDate]: [...(schedule[selectedDate] || []), newEvent],
      };

      setSchedule(newSchedule); // 상태 업데이트
      saveScheduleToLocalStorage(newSchedule); // 로컬 스토리지에 저장

      setNewEvent(""); // 입력 필드 초기화
      setSelectedDate(null); // 날짜 선택 초기화
    }
  };

  const handleDeleteEvent = (date, index) => {
    const updatedEvents = schedule[date].filter((_, idx) => idx !== index);
    const updatedSchedule = { ...schedule, [date]: updatedEvents };

    setSchedule(updatedSchedule); // 상태 업데이트
    saveScheduleToLocalStorage(updatedSchedule); // 로컬 스토리지에 저장
  };

  const loadScheduleFromLocalStorage = () => {
    const savedSchedule = localStorage.getItem("schedule");
    if (savedSchedule) {
      setSchedule(JSON.parse(savedSchedule));
    }
  };

  const saveScheduleToLocalStorage = (newSchedule) => {
    localStorage.setItem("schedule", JSON.stringify(newSchedule));
  };

  useEffect(() => {
    loadScheduleFromLocalStorage();
  }, []);

  const translateWeather = (weather) => {
    const weatherTranslations = {
      "clear sky": "맑음",
      "few clouds": "구름 조금",
      "scattered clouds": "흩어진 구름",
      "broken clouds": "흐림",
      "overcast clouds": "먹구름",
      "shower rain": "소나기",
      rain: "비",
      thunderstorm: "천둥/번개",
      snow: "눈",
      mist: "안개",
      haze: "연무",
      fog: "짙은 안개",
      tornado: "회오리바람",
    };

    // 해당 날씨가 있으면 변환된 값 반환, 없으면 그대로 반환
    return weather && weather.toLowerCase
      ? weatherTranslations[weather.toLowerCase()] || weather
      : weather;
  };

  const getWeatherImage = (weather) => {
    const weatherImages = {
      "clear sky": "/image/weather/ClearSky.PNG",
      "few clouds": "/image/weather/FewClouds.PNG",
      "scattered clouds": "/image/weather/ScatteredClouds.PNG",
      "broken clouds": "/image/weather/BrokenClouds.PNG",
      "overcast clouds": "/image/weather/OvercastClouds.PNG",
      "shower rain": "/image/weather/ShowerRain.PNG",
      rain: "/image/weather/Rain.PNG",
      thunderstorm: "/image/weather/Thunderstorm.PNG",
      snow: "/image/weather/Snow.PNG",
      mist: "/image/weather/Mist.PNG",
      haze: "/image/weather/Haze.PNG",
      fog: "/image/weather/Fog.PNG",
      tornado: "/image/weather/Tornado.PNG",
    };

    // 매핑된 이미지가 있으면 반환, 없으면 기본 이미지 반환
    return (
      weatherImages[weather.toLowerCase()] ||
      "/image/weather/DefaultWeather.PNG"
    );
  };

  const dateCellRender = (value) => {
    const dateString = value.format("YYYY-MM-DD");
    const listData = schedule[dateString] || [];

    return (
      <div className="custom-date-cell">
        <div>
          <div className="dateCellBtn">
            {validDates.includes(dateString) && (
              <Button
                type="primary"
                onClick={() => weatherModal(value)}
                className="weatherBtn"
              >
                날씨보기
              </Button>
            )}
          </div>
          <div>
            <Button
              type="primary"
              onClick={() => {
                setSelectedDate(dateString); // 날짜 선택
                dateAddModal(); // 일정 추가 모달 열기
              }}
              className="dateCell"
            ></Button>
          </div>
        </div>

        <ul style={{ paddingLeft: "10px", listStyle: "none" }}>
          {listData.map((item, index) => (
            <li key={index}>
              <div className="date">
                <Badge status="success" text={item} />
                <Button
                  className="deleteBtn"
                  size="small"
                  type="link"
                  onClick={(e) => {
                    e.stopPropagation(); // 클릭 시 모달이 뜨지 않도록
                    handleDeleteEvent(dateString, index); // 일정 삭제만
                  }}
                  style={{ marginLeft: 5 }}
                >
                  X
                </Button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    );
  };

  return (
    <div className="calendar-container">
      <div className="calendar-title">
        <div className="calendar-title-box">CALENDAR</div>
        <Calendar className="custom-calendar" cellRender={dateCellRender} />
      </div>

      {/* 일정 추가 Modal */}
      {selectedDate && isDateAddModalVisible && (
        <Modal
          title={`일정 추가: ${selectedDate}`}
          visible={isDateAddModalVisible}
          onCancel={() => setSelectedDate(null)}
          footer={null}
        >
          <Input
            value={newEvent}
            onChange={(e) => setNewEvent(e.target.value)}
            placeholder="새 일정을 입력하세요"
          />
          <Button
            className="dateAddBtn"
            type="primary"
            onClick={handleAddEvent}
            style={{ marginTop: 10 }}
          >
            추가
          </Button>
        </Modal>
      )}

      <div>
        {/* 날씨 모달 */}
        {isWeatherModalVisible && selectedDate && weatherData && (
          <Modal
            visible={isWeatherModalVisible}
            onCancel={() => setIsWeatherModalVisible(false)}
            footer={null}
          >
            <div className="weatherModal">
              <div className="weatherModalLeft">
                {/* 날씨 데이터에 기반한 정보 출력 */}
                <img
                  src="/image/clush_logo1.png"
                  className="weatherLeftImage"
                ></img>
                <div className="weatherTitle">날씨</div>
                <p>{selectedDate}</p>
                <p>기온: {weatherData[selectedDate]?.avg_temp}°C</p>
                <p>최고 기온: {weatherData[selectedDate]?.max_temp}°C</p>
                <p>최저 기온: {weatherData[selectedDate]?.min_temp}°C</p>
                <p>습도: {weatherData[selectedDate]?.humidity}%</p>
                <p>
                  날씨: {translateWeather(weatherData[selectedDate]?.weather)}
                </p>
              </div>
              <div className="weatherModalRight">
                <img
                  src={getWeatherImage(weatherData[selectedDate]?.weather)}
                  className="weatherRightImage"
                />
              </div>
            </div>
          </Modal>
        )}
      </div>
    </div>
  );
};

export default MyCalendar;
