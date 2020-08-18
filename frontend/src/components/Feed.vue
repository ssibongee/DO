<template>
  <div>
    <Navbar></Navbar>
    <div class="doContent">
      <div class="feature_feed">
        <div class="inner_feature">
          <h2 id="do_top do_feed">Feed</h2>
          <p class="top_desc">내가 좋아요한 글을 한 눈에</p>
          <div class="info_feed">
            좋아요
            <span class="feed_cnt">{{posts.length}}</span>
          </div>
        </div>
      </div>
      <div class="content">
        <v-row v-if=posts.length>
          <v-col cols="4" v-for="(post, index) in posts" :key="index+'_posts'">
            <v-hover>
              <template v-slot="{hover}">
                <v-card
                  @click="postdetail(post)"
                  height="380px"
                  max-width="350px"
                  :elevation="hover ? 8 : 2"
                >
                  <v-img
                    class="align-end"
                    height="200px"
                    :src="post.thumbnail"
                  />
                    <!-- 제목이 가로범위 초과시 ... -->
                    <v-card-title><h5>{{post.title}}</h5></v-card-title>
                    <v-card-text class="text--primary" style="height:65px">
                    <div>{{post.content}}</div>
                    <div v-if="!post.content">게시글 내용이 비어있습니다.</div>
                    <div>{{post.publishedTime}}</div>
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
              </template>
            </v-hover>
          </v-col>
        </v-row>
        <v-row v-else>
          게시글이 없습니다.
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'

AOS.init();

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
  name: 'Feed',
  components: {
    Navbar,
  },
  data() {
    return{
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
    this.postread()
  },
  methods: {
    postdetail(one_post) {
      storage.removeItem("pid")
      storage.setItem("pid", one_post.pid)
      this.$router.push({name: 'postdetail', params: {data: one_post}})
    },
    postread() {
      axios.get(API_URL+`api/v1/likes/${storage.getItem("uid")}`)
      .then(({data})=>{
        // 콘텐츠 미리보기 슬라이스
        data.forEach(el => {
          el.tmp = el.content
          // console.log(el)
          if (el.content.length > 40) {
            // 마크다운 사진 제외
            el.content = el.content.replace(/!\[.*\)+/, "")
            // 최대 길이 슬라이스
            el.content = el.content.slice(0, 40)
          }
          // 작성 날짜만 보이게 수정
          let year = el.publishedTime.slice(0,4);
          let month = el.publishedTime.slice(5,7);
          let day = el.publishedTime.slice(8,10);
          el.publishedTime = year+"년 "+month+"월 "+day+"일"
          this.posts = data
        });
      })
      .catch(err => console.log(err))
    }
  }
}
</script>

<style>
.inner_feature h2 {
  font-family: 'Inter', sans-serif;
  font-size: 24px;
  font-weight: 800;
}
.top_desc {
  padding:0 0;
  font-size: 24px;
  line-height: 1;
  letter-spacing: -0.6px;
  font-family: Noto Sans Light, sans-serif;
}
.info_feed {
  float: right;
  margin: -39px 0 0;
  color: #909090;
  font-size: 13px;
}
.feed_cnt {
  display: inline-block;
  font-size: 24px;
  vertical-align: middle;
  padding: 0 0 0 4px;
}
.doContent {
  max-width: 1150px;
  margin: 0 auto 0;
  padding: 0 20px;
}
.feature_feed{
  height: 133px;
  padding: 151px 0 90px;
  border-bottom: solid 2px black;;
}
.feature_main {
  width: 100%;
  height: 133px;
  padding: 151px 0 0;
}
.content {
  min-width: 1100px; 
  padding: 30px 0 0;
}
</style>