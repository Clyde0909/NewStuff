<template>
  <div>
    <news-modal
      v-bind:visible="newsVisible"
      :news="newsData2"
      @close="visible = newsInit()"
    ></news-modal>

    <div class="row">
      <div class="col-1"></div>

      <div class="col-10">
        <div class="row">
          <div class="container">
            <div class="block today">
              <div class="number today">
                <span style="width: 100px"> 오늘의 뉴스 현황 </span>
              </div>
            </div>

            <div class="block">
              <div class="number">
                <span>
                  <number
                    class="number"
                    :from="0"
                    :to="this.todayNewsData[4]"
                    :duration="2"
                  />
                  <span>%</span>
                </span>
              </div>
              <div class="string">정치</div>
            </div>
            <div class="block">
              <div class="number">
                <span>
                  <number
                    class="number"
                    :from="0"
                    :to="this.todayNewsData[5]"
                    :duration="2"
                  />
                  <span>%</span>
                </span>
              </div>
              <div class="string">경제</div>
            </div>
            <div class="block">
              <div class="number">
                <span>
                  <number
                    class="number"
                    :from="0"
                    :to="this.todayNewsData[2]"
                    :duration="2"
                  />
                  <span>%</span>
                </span>
              </div>
              <div class="string">사회</div>
            </div>
            <div class="block">
              <div class="number">
                <span>
                  <number
                    class="number"
                    :from="0"
                    :to="this.todayNewsData[3]"
                    :duration="2"
                  />
                  <span>%</span>
                </span>
              </div>
              <div class="string">생활/문화</div>
            </div>
            <div class="block">
              <div class="number">
                <span>
                  <number
                    class="number"
                    :from="0"
                    :to="this.todayNewsData[1]"
                    :duration="2"
                  />
                  <span>%</span>
                </span>
              </div>
              <div class="string">세계</div>
            </div>
            <div class="block">
              <div class="number">
                <span>
                  <number
                    class="number"
                    :from="0"
                    :to="this.todayNewsData[0]"
                    :duration="2"
                  />
                  <span>%</span>
                </span>
              </div>
              <div class="string">IT</div>
            </div>
          </div>
        </div>
        <br />
        <br />
        <div class="row mb-2">
          <div class="col-6 mb-4">
            <div class="container2">
              <div>
                <span style="font-size: calc(1.3rem + 0.6vw)">
                  실시간 이슈
                </span>
                <span style="font-size: calc(1rem + 0.6vw)">
                  {{ this.realHour }}
                </span>
                <span style="float: right"
                  ><ul class="wrapper">
                    <li class="icon facebook">
                      <span class="tooltip"
                        >현재 시각을 기준으로 이전 1시간 동안의 언급량 상위
                        20개의 키워드를 나타냅니다.<br />
                        키워드를 클릭하면 관련 기사 목록을 확인할 수 있습니다.
                      </span>
                      <span><i class="fab">i</i></span>
                    </li>
                  </ul></span
                >
              </div>
              <div class="mb-2">
                <button
                  :class="{
                    'main-btn-check': item.flag === 1,
                    'main-btn-uncheck': item.flag === 0,
                  }"
                  v-for="item in codeGroup"
                  :key="item.number"
                  @click="wordcloud(item.number)"
                >
                  {{ item.name }}
                </button>
              </div>
              <div
                id="word-cloud"
                style="width: 100%; text-align: center"
              ></div>
            </div>
          </div>

          <div class="col-6 mb-3">
            <div class="container2">
              <div class="spinner-div" v-if="this.newsData == null">
                <button class="btn">
                  <span class="spinner-border spinner-border-m"></span>
                  조회중입니다.
                </button>
              </div>
              <div else>
                <div
                  v-for="item in newsData"
                  :key="item.news_seq"
                  class="mb-1 main-news-list"
                  @click="modal(item.news_seq)"
                >
                  <div class="card">
                    <div class="row">
                      <div class="col-4">
                        <img
                          class="card-img main-card-img"
                          v-if="item.news_photo == ''"
                          style="object-fit: contail"
                          src="../../../src/assets/shin_logo.png"
                        />
                        <img
                          class="card-img main-card-img"
                          v-bind:src="item.news_photo"
                          v-else
                          alt="Card image"
                          style="object-fit: cover"
                          @error="replaceDefault"
                        />
                      </div>

                      <div class="col-8">
                        <div class="card-body">
                          <div class="mb-2">
                            <h5 class="card-title" style="text-align: left">
                              {{ item.news_Title }}
                            </h5>
                          </div>
                          <hr />
                          <div>
                            <p
                              class="card-text main-card"
                              style="text-align: left; font-size: 16px"
                            >
                              {{ item.news_desc }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <br />
        <div class="row mb-2">
          <div class="col-6 mb-3">
            <span style="font-size: calc(1.3rem + 0.6vw)">
              최고 빈도 키워드
            </span>

            <span style="float: right"
              ><ul class="wrapper">
                <li class="icon facebook">
                  <span class="tooltip"
                    >위 워드클라우드의 상위 7개 키워드의 언급량을 막대 그래프로
                    한 눈에 확인할 수 있습니다.</span
                  >
                  <span><i class="fab">i</i></span>
                </li>
              </ul></span
            >
            <div class="container3">
              <!-- <bar-chart :data="chartData"></bar-chart> -->
              <bar-chart
                :data="chartData"
                :colors="[
                  '#3366CC',
                  '#DC3912',
                  '#FF9900',
                  '#109618',
                  '#990099',
                  '#3B3EAC',
                  '#0099C6',
                ]"
              ></bar-chart>
            </div>
          </div>
          <div class="col-6 mb-3">
            <span style="font-size: calc(1.3rem + 0.6vw)">
              키워드 주간 기사량
            </span>

            <span style="float: right"
              ><ul class="wrapper">
                <li class="icon facebook">
                  <span class="tooltip"
                    >최고 빈도 키워드들의 주간 기사량 추이를 나타내는
                    그래프입니다.</span
                  >
                  <span><i class="fab">i</i></span>
                </li>
              </ul></span
            >
            <div class="container3">
              <div class="spinner-div2" v-if="this.lineData == null">
                <button class="btn">
                  <span class="spinner-border spinner-border-m"></span>
                  조회중입니다.
                </button>
              </div>
              <line-chart v-else :data="lineData"></line-chart>
            </div>
          </div>
        </div>

        <hr />
        <div v-if="this.authToken == null || this.positiveArticle == null">
          <!--로그인안됨  -->
          {{ this.ArticleMsg }}
        </div>
        <div v-else class="row mb-2">
          <div class="col-6 mb-3">
            <div>
              <h2>긍정적 추천 기사</h2>
              <br />
              <div>
                <div
                  class="mb-1 main-news-list"
                  @click="modal(this.positiveArticle.newsSeq)"
                >
                  <div class="card">
                    <div class="row">
                      <div class="col-4">
                        <img
                          class="card-img main-card-img"
                          v-if="this.positiveArticle.newsPhoto == ''"
                          style="object-fit: contain"
                          src="../../../src/assets/shin_logo.png"
                        />
                        <img
                          class="card-img main-card-img"
                          v-bind:src="this.positiveArticle.newsPhoto"
                          v-else
                          alt="Card image"
                          style="object-fit: cover"
                          @error="replaceDefault"
                        />
                      </div>

                      <div class="col-8">
                        <div class="card-body">
                          <div class="mb-2">
                            <h5 class="card-title" style="text-align: left">
                              {{ this.positiveArticle.newsTitle }}
                            </h5>
                          </div>
                          <hr />
                          <div>
                            <p
                              class="card-text recommend-main-card"
                              style="text-align: left"
                            >
                              {{ this.positiveArticle.newsDesc }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-6 mb-3">
            <div>
              <h2>부정적 추천 기사</h2>
              <!-- card -->
              <br />
              <div>
                <div
                  class="mb-1 main-news-list"
                  @click="modal(this.negativeArticle.newsSeq)"
                >
                  <div class="card">
                    <div class="row">
                      <div class="col-4">
                        <img
                          class="card-img main-card-img"
                          v-if="this.negativeArticle.newsPhoto == ''"
                          style="object-fit: contain"
                          src="../../../src/assets/shin_logo.png"
                        />
                        <img
                          class="card-img main-card-img"
                          v-bind:src="this.negativeArticle.newsPhoto"
                          v-else
                          alt="Card image"
                          style="object-fit: cover"
                          @error="replaceDefault"
                        />
                      </div>

                      <div class="col-8">
                        <div class="card-body">
                          <div class="mb-2">
                            <h5 class="card-title" style="text-align: left">
                              {{ this.negativeArticle.newsTitle }}
                            </h5>
                          </div>
                          <hr />
                          <div>
                            <p
                              class="card-text recommend-main-card"
                              style="text-align: "
                            >
                              {{ this.negativeArticle.newsDesc }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-1"></div>
  </div>
</template>

<script>
import axios from "axios";
import VueWordCloud from "vuewordcloud";
import wordcloud from "vue-wordcloud";
import img from "@/assets/default.png";
import newsModal from "./newsModal.vue";
import VueNumber from "vue-number-animation";
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = "https://j6a406.p.ssafy.io/api";

axios.defaults.paramsSerializer = function (paramObj) {
  const params = new URLSearchParams();
  for (const key in paramObj) {
    params.append(key, paramObj[key]);
  }
  return params.toString();
};

export default {
  components: { VueWordCloud, wordcloud, newsModal, VueNumber },
  data() {
    return {
      realHour: null,
      newsData2: {},
      newsVisible: false,
      newsData: null,
      codeGroup: [
        { number: 0, name: "전체", flag: 1 },
        { number: 100, name: "정치", flag: 0 },
        { number: 101, name: "경제", flag: 0 },
        { number: 102, name: "사회", flag: 0 },
        { number: 103, name: "생활/문화", flag: 0 },
        { number: 104, name: "세계", flag: 0 },
        { number: 105, name: "IT/과학", flag: 0 },
      ],

      words: [
        { text: "글자1", size: 40, color: "#f6535d" },
        { text: "글자2", size: 36, color: "#377cc4" },
        { text: "글자3", size: 32, color: "#b168e0" },
        { text: "글자4", size: 28, color: "#1a9a9f" },
        { text: "글자5", size: 24, color: "#fac302" },
        { text: "글자6", size: 24, color: "#d86898" },
        { text: "글자7", size: 24, color: "#f9870e" },
        { text: "글자8", size: 18, color: "#6E6E6E" },
        { text: "글자9", size: 18, color: "#6E6E6E" },
        { text: "글10", size: 18, color: "#6E6E6E" },
        { text: "글11", size: 18, color: "#6E6E6E" },
        { text: "글12", size: 18, color: "#6E6E6E" },
        { text: "글13", size: 18, color: "#6E6E6E" },
        { text: "글14", size: 18, color: "#6E6E6E" },
        { text: "글15", size: 18, color: "#6E6E6E" },
        { text: "글16", size: 18, color: "#6E6E6E" },
        { text: "글17", size: 18, color: "#6E6E6E" },
        { text: "글18", size: 18, color: "#6E6E6E" },
        { text: "글19", size: 18, color: "#6E6E6E" },
        { text: "글20", size: 18, color: "#6E6E6E" },
      ],
      chartData: [],
      lineData: null, //line data

      todayNewsData: [0, 0, 0, 0, 0, 0],
      todayNews: null,
      userSeq: localStorage.getItem("userSeq") || null,
      authToken: localStorage.getItem("authToken") || null,
      negativeArticle: null,
      positiveArticle: null,
      ArticleSuccess: false,
      ArticleMsg: "",
    }; //return
  }, //data

  created() {
    this.wordcloud(0);
    // this.genLayout();
    // this.generate(0);
    this.getTodayNews();
    var endHour = new Date().getHours();
    var startHour = endHour - 1;
    if (endHour == 0) {
      startHour = 23;
      endHour = "00";
    } else if (endHour == 1) {
      startHour = "00";
    }

    this.realHour = "";
    this.realHour += "(" + startHour + ":00 ~ " + endHour + ":00)";
    if (this.userSeq != null && this.userSeq != "") {
      this.getRecommendArticle(this.userSeq);
    }
  },
  methods: {
    getRecommendArticle(userSeq) {
      axios
        .get(`${SERVER_HOST}/recom/keyword`, {
          params: {
            userSeq: userSeq,
          },
        })
        .then((res) => {
          //유저 추천 기사 응답 성공
          if (res.data.statusCode == 200) {
            this.ArticleSuccess = true;
            this.negativeArticle = res.data.data.negative;
            this.positiveArticle = res.data.data.positive;
          }

          this.ArticleMsg = res.data.message;
        })
        .catch((err) => {});
    },
    generate(category) {
      axios
        .get(`${SERVER_HOST}/news/main/wordcloud`, {
          params: {
            codeGroup: category,
          },
        })
        .then((res) => {
          this.chartData = [];
          var keywords = [];

          for (var i = 0; i < 7; i++) {
            var temp = [res.data.data[i].keyword, res.data.data[i].count];
            keywords.push(res.data.data[i].keyword);
            this.chartData.push(temp);
          }

          this.lineData = null;
          this.LinechartMake(keywords);
        })
        .catch((err) => {});
    },

    LinechartMake(keyword) {
      axios
        .get(`${SERVER_HOST}/news/keyword/trend/week`, {
          params: {
            // keywords : obj,
            keywords: keyword,
          },
        })
        .then((res) => {
          this.lineData = [
            { name: "", data: {} },
            { name: "", data: {} },
            { name: "", data: {} },
            { name: "", data: {} },
            { name: "", data: {} },
            { name: "", data: {} },
            { name: "", data: {} },
          ];

          for (var i = 0; i < 7; i++) {
            var values = Object.values(res.data.data[i].stat); //받은 result value들만 따로 정제
            var temp = {}; //value를 속성, 값으로 만들어줄 객체
            for (var j = 0; j < values.length; j++) {
              let label = values[j].label;
              temp[label] = values[j].count; //temp 객체에 label 속성과 count 값 할당
            }
            this.lineData[i].name = keyword[i];
            this.lineData[i].data = temp;
          }
        })
        .catch((err) => {
          alert("그래프 데이터 없음");
        });
    },
    newsInit: function () {
      this.newsVisible = false;
    },

    modal: function (seq) {
      //seq로  뉴스 상세정보 조회
      axios({
        method: "get",
        url: `${SERVER_HOST}/news/detail`,
        params: {
          newsSeq: seq,
        },
      })
        .then((res) => {
          this.newsData2 = res.data.data;
          this.newsVisible = !this.newsVisible;
        })
        .catch((err) => {});
    },

    replaceDefault: function (e) {
      e.target.src = img;
    },
    wordcloud(number) {
      this.generate(number);

      for (let index = 0; index < this.codeGroup.length; index++) {
        if (this.codeGroup[index].number === number) {
          this.codeGroup[index].flag = 1;
        } else {
          this.codeGroup[index].flag = 0;
        }
      }

      axios({
        method: "get",
        url: `${SERVER_HOST}/news/main/wordcloud`,
        params: {
          codeGroup: number,
        },
      })
        .then((res) => {
          const data = res.data.data;
          let flag = false;

          if (this.words[0].text === "글자1") {
            flag = true;
          }

          for (let index = 0; index < 20; index++) {
            this.words[index].text = data[index].keyword;
          }

          if (flag) {
            axios({
              method: "get",
              url: `${SERVER_HOST}/news/keyword`,
              params: {
                keyword: this.words[0].text,
                page: 1,
                size: 3,
              },
            })
              .then((res) => {
                this.newsData = res.data.data;
              })
              .catch((err) => {});
          }

          this.genLayout();
        })
        .catch((err) => {});
    },
    check(d, d3) {
      this.newsData = null;

      d3.selectAll("text").style("fill-opacity", 0.5);
      d3.select("." + d.text).style("fill-opacity", 1);

      axios({
        method: "get",
        url: `${SERVER_HOST}/news/keyword`,
        params: {
          keyword: d.text,
          page: 1,
          size: 3,
        },
      })
        .then((res) => {
          this.newsData = res.data.data;
        })
        .catch((err) => {});
    },

    genLayout() {
      const cloud = require("d3-cloud");
      cloud()
        .words(this.words)
        .padding(2)
        .rotate(0)
        .font("serif")
        .fontSize(function (d) {
          return d.size;
        })
        .on("end", this.end)
        .spiral("archimedean")
        .start();
      // .stop();
    },
    end(words) {
      const d3 = require("d3");
      const width = 660;
      const height = 400;
      const text = "";
      d3.select("#word-cloud")
        .html("")
        .append("svg")
        .attr("preserveAspectRatio", "xMinYMin meet")
        .attr("width", width)
        .attr("height", height)
        // .attr("class", "span--2 long--2")
        .style("background", "white")
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")") //g를 중심으로 단어그림 svg 중심으로 이동
        .selectAll("text")
        .data(words)
        .enter()
        .append("text")
        .attr("class", (d) => {
          return d.text;
        })
        .style("font-size", (d) => {
          return d.size * 1.2 + "px";
        })
        .style("cursor", "pointer")
        .style("fill", (d) => {
          return d.color;
        }) //색지정
        .style("fill-opacity", 1) //투명도 조절
        .style("font-family", "Impact")
        .style("font-weight", "bold")
        .attr("text-anchor", "middle")
        .attr("transform", (d) => {
          return (
            "translate(" +
            [d.x * 2.5, d.y * 1.5] +
            ") rotate (" +
            d.rotate +
            ")"
          );
        })
        .text((d) => d.text)
        .on("click", (ev, d) => {
          this.check(d, d3);
        })
        .on("mouseover", function () {
          d3.select(this).style("font-size", (d) => {
            return d.size * 1.2 + 3 + "px";
          });
        })
        .on("mouseout", function () {
          d3.select(this).style("font-size", (d) => {
            return d.size * 1.2 - 3 + "px";
          });
        });
    },
    handleMouseOver(d) {
      d.style("fill", "blue");
    },

    getTodayNews() {
      axios
        .get(`${SERVER_HOST}/news/today`)
        .then((res) => {
          this.todayNewsData = res.data.data;

          var keys = Object.keys(this.todayNewsData);
          var values = Object.values(this.todayNewsData);
          var news = "";
          for (var i = 0; i < keys.length - 1; i++) {
            news += keys[i];
            const per = values[i].percent;
            news += " " + per.toFixed(1) + "% ";
            this.todayNewsData[i] = parseFloat(per.toFixed(1));
          }
          this.todayNews = news;
        })
        .catch((err) => {});
    },
  }, //methods
};
</script>

d3.layout.cloud(): 클라우드 레이아웃 생성 on(type, listner): 이벤트 리스너.
현재는 word와 end만 지원 word 는 단어 하나하나 위치시켜놓을 때마다 발생. 해당
word가 argument로 들어온다. end 는 모든 단어를 다 위치 시켜놓을 떄 발생. 모든
단어들과 경계선 좌표([{x0, y0}, {x1, y1}])가 argument로 들어온다. start():
레이아웃 알고리즘 시작. 단어들을 큰 것부터 넣기 시작하고 충돌을 체크해서
발생할때마다 다른 위치로 넣는다. (만약에 들어갈 자리가 없을 경우 포함되지 않으니
유의) stop(): 레이아웃 알고리즘 멈춤. timeInterval([time]): 내부적으로
setInterval 사용하여 event loop가 계속 되도록 해준다. default는 Infinity words:
클라우드에 띄울 단어들. default는 [] size([size]): 레이아웃의 크기를
결정한다.(width, height) default는 [1, 1] font([font]): 단어들의 폰트를
결정한다. default는 serif fonrStyle([fontStyle]): 폰트 스타일을 결정한다.
default는 normal fontWeight([fontWeight]): fontWeight을 결정한다. default는
'normal' fontSize([fontSize]): font 크기를 결정한다. (예. {text: 'word', value:
30} rotate([rotate]): 각 단어의 회전각을 결정. function() { return
(~~(Math.random() * 6) - 3) * 30; } text([text]): words에서 넣은 값에서 표시할
것을 명시한다. 예를 들어 words에 {text: 'word', value: 30 }를 넣었다면
function(d) { return d.text;} spiral([spiral]): 단어를 위치시킬 떄 사용하는
나선형의 형태를 결정할 수 있는데 archimedean(원형) 또는 rectangular를 선택할 수
있으며 함수로 임의로 만들 수도 있다. default로 내장된 archimedean을 사용한다.
padding([padding]): 각 단어의 padding을 결정. default는 1. random([random]):
내부적으로 단어를 초기 위치와 시계, 반시계 방향 각도를 결정할 때 사용하는 랜덤
숫자 생성기. 0 이상 1 미만 숫자가 들어가야하며 default는 Math.random
canvas([canvas]): 캔버스 생성기

<style scoped>
.main-news-list {
  cursor: pointer;
}

.spinner-div {
  line-height: 470px;
  text-align: center;
}
.spinner-div2 {
  line-height: 350px;
  text-align: center;
}

.main-btn-check {
  border: none;
  border-bottom: 3px solid blue;

  margin-right: 10px;
  background-color: white;
}

.main-btn-uncheck {
  border: none;
  margin-right: 10px;
  background-color: white;
}

.main-card-img {
  height: 156px;
  object-fit: cover;
  margin: 0px;
}
.recommend-main-card
{
   display: -webkit-box;
  word-wrap: break-word;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 70px;
}
.main-card {
  display: -webkit-box;
  word-wrap: break-word;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 70px;
}

#word-cloud {
  /* border  : 1px solid blue; */
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
}

@import url("https://fonts.googleapis.com/css?family=Lato");
* {
  transition: all 0.3s ease;
}
body {
  display: flex;
  width: 100vw;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background: #b8d2ff;
  font-family: "Lato", Arial;
  color: #263238;
}
body .container {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  height: 100px;
  background: #fff;
  align-items: center;
  border-radius: 8px;
}
body .container2 {
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  height: 480px;
  background: #fff;
  align-items: center;
  border-radius: 8px;
  box-shadow: 0px 10px 30px rgba(139, 139, 139, 0.1);
}
body .container3 {
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  height: 400px;
  background: #fff;
  align-items: center;
  border-radius: 8px;
  box-shadow: 0px 10px 30px rgba(139, 139, 139, 0.1);
}
body .container .block {
  text-align: center;
  height: 100%;
  width: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

h3 {
}

.block .today {
  width: 300px;
}
body .container .block:hover {
  color: #4d75f6;
}
body .container .block .number {
  font-size: 32px;
  font-weight: bold;
}
body .container .block .string {
  font-size: 15px;
  line-height: 18px;
  opacity: 0.8;
}

/* tooltip */
/*
    Auther: Abdelrhman Said
*/

@import url("https://fonts.googleapis.com/css2?family=Poppins&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

*:focus,
*:active {
  outline: none !important;
  -webkit-tap-highlight-color: transparent;
}

html,
body {
  display: grid;
  height: 100%;
  width: 100%;
  font-family: "Poppins", sans-serif;
  place-items: center;
  background: linear-gradient(315deg, #ffffff, #d7e1ec);
}

.wrapper {
  display: inline-flex;
  list-style: none;
}

.wrapper .icon {
  position: relative;
  background: rgb(255, 254, 254);
  border-radius: 50%;
  padding: 15px;
  margin: 10px;
  width: 10px;
  height: 10px;
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip {
  position: absolute;
  top: 0;
  font-size: 14px;
  background: #ffffff;
  color: #ffffff;
  padding: 5px 8px;
  border-radius: 5px;
  width: 300px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip::before {
  position: absolute;
  content: "";
  height: 8px;
  width: 5px;
  background: #ffffff;
  bottom: -3px;
  left: 50%;
  transform: translate(-50%) rotate(45deg);
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .icon:hover .tooltip {
  top: -45px;
  width: 250px;
  opacity: 1;
  visibility: visible;
  pointer-events: auto;
}

.wrapper .icon:hover span,
.wrapper .icon:hover .tooltip {
  text-shadow: 0px -1px 0px rgba(0, 0, 0, 0.1);
}

.wrapper .facebook:hover,
.wrapper .facebook:hover .tooltip,
.wrapper .facebook:hover .tooltip::before {
  background: #1877f2;
  color: #ffffff;
}
</style>
