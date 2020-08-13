<template>
  <div id=app>
  <!-- 네비게이션 바 -->
  <Navbar></Navbar>
  <div class="toptab">
    <div></div>
    <p class="doasis">
      <span><em style="color: #6987f7; padding-left:3px">D</em>eveloper</span>
      <span><em style="color: #08d3bc;">O</em>asis</span>
    </p>
    <v-tabs v-model="tab" 
      class="nav"
      background-color="transparent"
      color="black"
      slider-color="#6987f7"
      slider-size="6"
      >
      <v-tab @click="postread(item)" v-for="item in items" :key="item">
        <div>{{ item }}</div>
      </v-tab>
    </v-tabs>
  </div>
  <div class="contain">
    <div class="content_area">
      <!-- 게시글 탭 -->
      <!-- 게시글 미리보기 -->
      <v-row>
        <v-col>
          <v-tabs-items v-model="tab">
            <v-tab-item v-for="(item) in items" :key="item">
              <v-row v-if="posts.length">
                <!-- <div v-if="item.length"> -->
                  <v-col col="12" md="6" lg="4" v-for="(post, index) in posts" :key="index+'_posts'">
                    <v-card
                      hover
                      @click="postdetail(post)"
                    >
                      <v-img
                        class="white--text align-end"
                        height="200px"
                        :src="post.thumbnail"
                      >
                        <v-card-title>{{post.title}}</v-card-title>
                      </v-img>

                      <v-card-subtitle class="pb-0">{{ item }}</v-card-subtitle>

                      <v-card-text class="text--primary">
                        <div>{{post.content}}</div>
                        <div v-if="!post.content">게시글 내용이 비어있습니다.</div>
                        <div>작성 날짜: {{post.publishedTime}}</div>
                      </v-card-text>

                      <v-card-actions>
                        <v-btn
                          color="orange"
                          text
                        >
                          Share
                        </v-btn>

                        <v-btn
                          color="orange"
                          text
                        >
                          Explore
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                </v-row>
                <v-row v-else>
                  게시글이 없습니다.
              </v-row>
            </v-tab-item>
          </v-tabs-items>
        </v-col>
      </v-row>
    </div>
    </div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
import ScrollOut from "scroll-out";

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
  name: 'Home',
  data() {
    return {
      tab: null,
      items: [
        '최신', '조회수', '좋아요'
      ],
      posts : {
        pid: '',
        title: '',
        content: '',
        publishedTime: '',
        thumbnail:'',
      }
    }
  },
  created() {
    this.postread('latest')
  },
  components: {
    Navbar
  },
  mounted() {
    this.so = ScrollOut({
      scope:this.$el
    })
  },
  methods: {
    postdetail(one_post) {
      storage.removeItem("pid")
      storage.setItem("pid", one_post.pid)
      this.$router.push({name: 'postdetail', params: {data: one_post}})
    },
    postread(item) {
      let lowercase_item = item.toLowerCase()
      axios.get(API_URL+`api/v2/${lowercase_item}`)
      .then(({data})=>{
        // 콘텐츠 미리보기 슬라이스
        data.forEach(el => {
          if (el.content.length > 40) {
            el.tmp = el.content
            // 마크다운 사진 제외
            el.content = el.content.replace(/!\[.*\)+/, "")
            // 최대 길이 슬라이스
            el.content = el.content.slice(0, 40)
            // 작성 날짜만 보이게 수정
            el.publishedTime = el.publishedTime.slice(0, 10)
          }
          this.posts = data
        });
      })
      .catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>
/* @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap'); */
@import url('http://fonts.googleapis.com/earlyaccess/notosanskr.css');

.doasis{
  position: relative;
  top: -50px;
  line-height: 0.9;
  letter-spacing: 1px;
}
.doasis span {
  display: block;
  font-size:48px;
  font-family: 'Inter', sans-serif;
  font-weight: 600;
}
em {
  font-style:normal;
}
.toptab {
  padding-top : 150px;
  padding-left: 20px;
  max-width: 1150px;
  margin: 35px auto 0;
}
.contain {
  /* padding-top : 150px; */
  max-width: 1150px;
  margin: 35px auto 0;
  padding: 0 20px;
}
.v-tab {
  letter-spacing: -2px;
  margin-right:20px;
  font-size: 30px;
  font-family: 'Noto Sans DemiLight','Apple SD Gothic','맑은고딕','Nanum Gothic',sans-serif;;
}
.content_area {
}
</style>
