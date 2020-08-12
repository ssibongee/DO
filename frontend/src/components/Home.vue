<template>
  <div id=app>
  <!-- 네비게이션 바 -->
    <div>
      <Navbar></Navbar>
    </div>
    <v-container>
      <!-- 게시글 탭 -->
      <v-tabs v-model="tab" grow class="nav">
        <v-tab @click="postread(item)" v-for="item in items" :key="item">
          <div>{{ item }}</div>
        </v-tab>
      </v-tabs>
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
                      @click="postdetail(post.pid)"
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
    </v-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'

const API_URL = 'http://i3a507.p.ssafy.io:8081/'

export default {
  name: 'Home',
  data() {
    return {
      tab: null,
      items: [
        'Latest', 'Hits', 'Likes'
      ],
      // content 크기따라  주석 처리 
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
    
  },
  components: {
    Navbar
  },
  mounted() {
  },
  methods: {
    postdetail(pid) {
      axios.get(API_URL+`api/v2/p/${pid}`)
        .then(res => {
          this.$router.push({
            name: 'postdetail', 
            params: { 
              username:res.data.author, 
              title:res.data.title, 
              content: res.data.content, 
              data: res.data}})
        })
    },
    postread(item) {
      let lowercase_item = item.toLowerCase()
      axios.get(API_URL+`api/v2/${lowercase_item}`)
      .then(({data})=>{
        // 콘텐츠 미리보기 슬라이스
        data.forEach(el => {
          if (el.content.length > 40) {
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
.container {
  padding-top : 120px;
}
#side {
  margin-left:2vw;
}
</style>
