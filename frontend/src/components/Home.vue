<template>
  <div id=app>
  <!-- 네비게이션 바 -->
  <Navbar></Navbar>
    <v-container>
    <!-- 게시글 탭 -->
    <v-tabs v-model="tab" grow class="nav">
      <v-tab v-for="item in items" :key="item">
        {{ item }}
      </v-tab>
    </v-tabs>
    
    <v-row>
      <v-col>
        <v-tabs-items v-model="tab">
          <v-tab-item v-for="(item) in items" :key="item">
            <v-row v-if="posts.length">
              <!-- <div v-if="item.length"> -->
                <v-col col="12" md="6" lg="4" v-for="(post, index) in posts" :key="index+'_posts'">
                  <v-card
                    hover
                    @click="testpostdetail(post.pid)"
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
                </v-col>
              </v-row>
              <v-row v-else>
                게시글이 없습니다.
            </v-row>
          </v-tab-item>
        </v-tabs-items>
      </v-col>
      <v-col id="side" class="hidden-sm-and-down" cols="3">
        <Sidebar/>
      </v-col>
    </v-row>
  </v-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import Sidebar from '../components/Sidebar.vue'
import axios from 'axios'

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

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
      // posts: []
    }
  },
  created() {
    axios
    .get(API_URL+'api/v2/latest')
    .then(({data})=>{
      // 콘텐츠 미리보기 슬라이스
      data.forEach(el => {
        if (el.content.length > 40) {
          // 마크다운 사진 제외
          el.content = el.content.replace(/!\[.*\)+/, "")
          // 최대 길이 슬라이스
          el.content = el.content.slice(0, 40)
        }
        this.posts = data
      });
    })
  },
  components: {
    Navbar,
    Sidebar
  },
  mounted() {
  },
  methods: {
    testpostdetail(pid) {
      axios.get(API_URL+`api/v2/p/${pid}`)
        .then(res => {
          // console.log(res)
          this.$router.push({name: 'postdetail', params: { username:res.data.author, title:res.data.title, content: res.data.content, data: res.data}})
        })
        .catch(err => console.log(err))
    },
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
/* .colortest {
  background-color : 
} */
</style>
