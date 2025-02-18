import React, { useState, useEffect } from "react";
import { Carousel } from "antd"; // Carousel도 여기서 가져오기
import { Card, Space } from "antd";
import "./Home.css";
import AOS from "aos";
import "aos/dist/aos.css";
function Home() {
  useEffect(() => {
    AOS.init({
      duration: 1000, // 애니메이션 지속 시간 (ms)
      once: true, // 스크롤 한 번만 애니메이션 실행
      easing: "ease-in-out", // 애니메이션 가속도
    });
  }, []);

  return (
    <div>
      {/* 슬라이드 시작 */}
      <Carousel
        autoplay
        autoplaySpeed={2000}
        dots={{ className: "custom-dots" }}
      >
        <div>
          <div className="videoContainer">
            <video autoPlay muted playsInline className="video">
              <source src="/image/slide1.mp4" type="video/mp4" />
            </video>
            <div className="textOverlay">
              <div style={{ fontSize: "36px", marginTop: "20%" }}>
                Cloud Native 전문 기업 ​Clush Company
              </div>
              <br></br>
              <div style={{ fontSize: "18px" }}>
                국내에서 가장 많은 Application Modernization <br></br>프로젝트
                수행을 통해 클라우드 Cloud Native <br></br>기술 역량과 노하우,
                플랫폼을 보유하고 있습니다.
              </div>
            </div>
          </div>
        </div>
        <div>
          <div className="imageContainer">
            <img src="/image/slide2.jpg" alt="slide 2" className="slideImage" />
            <div className="textOverlay">
              <div style={{ fontSize: "30px", marginTop: "20%" }}>
                Cloud Native Application 개발
              </div>
              <br></br>
              <div style={{ fontSize: "18px" }}>
                자체 플랫폼인 Clush Kube와 검증된 개발 방법론을 기반으로 고객의
                비즈니스 혁신을 위한 <br></br>Cloud Native Tech.를 적용하여{" "}
                <br></br>
                Cloud Native Application 개발 및 고도화를 지원합니다
              </div>
            </div>
          </div>
        </div>
        <div>
          <div className="imageContainer">
            <img src="/image/slide3.png" alt="slide 3" className="slideImage" />
            <div className="textOverlay">
              <div style={{ fontSize: "22px", marginTop: "80%" }}>
                CLUSH는 국내 최초의 컨테이너 클라우드 <br></br>서비스 전문가
                집단으로 도커 및 DevOps에 <br></br>최적화된 조직입니다.
              </div>
              <br></br>
              <div style={{ fontSize: "16px" }}>
                특히, Kubernetes를 기반으로 하는 기업용 대규모 분산 DevOps,
                인공지능 분석 플랫폼에 특화된 기업입니다.
              </div>
            </div>
          </div>
        </div>
        <div>
          <div className="imageContainer">
            <img src="/image/slide4.jpg" alt="slide 4" className="slideImage" />
            <div className="textOverlay">
              <div style={{ fontSize: "36px", marginLeft: "10%" }}>
                AI/Data 플랫폼 구축 및 <br></br>생성형 AI 도입
              </div>
              <div style={{ fontSize: "16px", marginLeft: "10%" }}>
                Clush는 데이터 협업플랫폼, Gen AI Ops, 서비스 포탈 등 다양한
                플랫폼을 기반으로 전사가 활용 할 수 있는 AI, Bigdata 플랫폼을
                구축하고 활용할 수 있도록 지원합니다.
              </div>
            </div>
          </div>
        </div>
      </Carousel>
      {/* 슬라이드 종료 */}

      {/* Body 시작 */}
      <div className="body">
        <div className="bodyText" data-aos="fade-down" data-aos-duration="1300">
          <div className="bodyText1">
            CLUSH는 국내 최초의 컨테이너 클라우드 서비스 전문가 집단으로 <br />{" "}
            도커 및 DevOps에 최적화된 조직입니다.
          </div>
          <br />
          <div className="bodyText2">
            특히, Kubernetes를 기반으로 하는 기업용 대규모 분산 DevOps, 인공지능
            분석 플랫폼에 특화된 기업입니다.
          </div>
        </div>

        <div
          className="cardBody"
          data-aos="fade-down"
          data-aos-duration="1500"
          data-aos-offset="20"
        >
          <div style={{ fontSize: "40px", marginLeft: "10%" }}>
            Business PortFolio
          </div>
          <Space direction="horizontal" size="large" className="cardContainer">
            <Card
              hoverable
              className="card"
              cover={<img src="/image/docker.png" className="cardImg" />}
            >
              <div className="cardDesc">
                <div className="cardDesc1_1">Docker Platform</div>
                <br></br>
                <div className="cardDesc1_2">
                  개발부터 운영까지 <br></br> 일원화 플랫폼
                </div>
              </div>
            </Card>
            <Card
              hoverable
              className="card"
              cover={<img src="/image/kubernetes.png" className="cardImg" />}
            >
              <div className="cardDesc">
                <div className="cardDesc1_1">Certified Kubernetes</div>
                <br></br>
                <div className="cardDesc1_2">
                  글로벌 인증을 획득한 <br></br>클라우드 전용 SW 솔루션
                </div>
              </div>
            </Card>
            <Card
              hoverable
              className="card"
              cover={<img src="/image/ai.png" className="cardImg" />}
            >
              <div className="cardDesc">
                <div className="cardDesc1_1">
                  AI Ops<br></br>
                </div>
                <br></br>
                <div className="cardDesc1_2">
                  OpenSource를 활용한 <br></br> AI분석 Workload 플랫폼
                </div>
              </div>
            </Card>
            <Card
              hoverable
              className="card"
              cover={<img src="/image/msa.png" className="cardImg" />}
            >
              <div className="cardDesc">
                <div className="cardDesc1_1"> Dev Ops</div>
                <br></br>
                <div className="cardDesc1_2">
                  Clous Native <br></br> Application을 위한 <br></br> MSA 자동화
                  솔루션
                </div>
              </div>
            </Card>
          </Space>
        </div>

        {/* Body1 */}
        <div
          className="body1"
          data-aos="fade-right"
          data-aos-duration="1400"
          data-aos-offset="20"
        >
          <div className="body1_Left">
            <img src="/image/body1.jpg" className="body1_Image1" />
          </div>
          <div className="body1_Right">
            <div className="body1_Box1">01</div>
            <div className="body1_Box2">Container Cloud</div>
            <div className="body1_Box3">
              Kubernetes부터 Mesos Architecture까지 다양한 Container Platform의
              전문가 집단입니다. 국내 초창기인 2017년부터 현재까지 최고의
              서비스를 제공받으실 수 있습니다.
            </div>
            <div className="body1_Box4">구축사례</div>
            <div className="body1_Box5">
              <img src="/image/logo/skhynix.webp" className="body1_Image2" />
              <img src="/image/logo/woori.png" className="body1_Image2" />
              <img src="/image/logo/magnachip.webp" className="body1_Image2" />
              <img src="/image/logo/keyfoundry.jpg" className="body1_Image2" />
              <img src="/image/logo/joia.jpg" className="body1_Image2" />
              <img src="/image/logo/asta.jpg" className="body1_Image2" />
            </div>
          </div>
        </div>

        {/* Body2*/}
        <div
          className="body2"
          data-aos="fade-left"
          data-aos-duration="1400"
          data-aos-offset="20"
        >
          <div className="body2_Left">
            <div className="body2_Box1">02</div>
            <div className="body2_Box2">
              Enterprise Cloud Native Application
            </div>
            <div className="body2_Box3">
              2017년부터 구축한 MSA(Micro Service Architecture)기반의 클라우드
              네이티브 어플리케이션들은 최적의 SaaS 환경을 제공합니다. 특히,
              BIZMICRO는 Certified Kubernetes 인증을 획득한 국제 공인 MSA 기반의
              기업용 SW입니다.
            </div>
            <div className="body2_Box4">주요 기능</div>
            <div className="body2_Box5">
              <li>Cloud Enterprise Portal</li>
              <li>Cloud Wiki Platform</li>
              <li>Cloud Team Work</li>
              <li>Cloud BigData Work</li>
            </div>
          </div>
          <div className="body2_Right">
            <img src="/image/body2.jpg" className="body2_Image1" />
          </div>
        </div>

        {/* Body3 */}
        <div
          className="body3"
          data-aos="fade-right"
          data-aos-duration="1400"
          data-aos-offset="20"
        >
          <div className="body3_Left">
            <img src="/image/body3.jpg" className="body3_Image1" />
          </div>
          <div className="body3_Right">
            <div className="body3_Box1">03</div>
            <div className="body3_Box2">AI Platform</div>
            <div className="body3_Box3">
              Open Source를 활용한 인공지능 모델 개발 및 학습, 운영 배포를 위한
              Data Governance Platform 입니다.
            </div>
            <div className="body3_Box4">구축사례</div>
            <div className="body3_Box5">
              <img src="/image/logo/woori.png" className="body3_Image2" />
            </div>
            <div className="body3_Box6">주요 기능</div>
            <div className="body3_Box7">
              <li>Cloud Enterprise Portal</li>
              <li>Cloud Wiki Platform</li>
              <li>Cloud Team Work</li>
              <li>Cloud BigData Work</li>
            </div>
          </div>
        </div>

        {/* Body4 */}
        <div
          className="body4"
          data-aos="fade-left"
          data-aos-duration="1400"
          data-aos-offset="20"
        >
          <div className="body4_Left">
            <div className="body4_Box1">04</div>
            <div className="body4_Box2">DevOps Platform</div>
            <div className="body4_Box3">
              개발부터 운영까지 일원화된 플랫폼을 제공합니다.어렵기만한 MSA
              구현을 손쉽게 제공할 수 있는 Development Pipeline을 제공합니다.
            </div>
            <div className="body4_Box4">구축사례</div>
            <div className="body4_Box5">
              <img src="/image/logo/skhynix.webp" className="body4_Image2" />
              <img src="/image/logo/woori.png" className="body4_Image2" />
              <img src="/image/logo/magnachip.webp" className="body4_Image2" />
              <img src="/image/logo/keyfoundry.jpg" className="body4_Image2" />
            </div>
          </div>

          <div className="body4_Right">
            <img src="/image/body4.jpg" className="body4_Image1" />
          </div>
        </div>
      </div>

      {/* Body 종료 */}
    </div>
  );
}

export default Home;
