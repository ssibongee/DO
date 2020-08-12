<template>
  <!-- navbar(로고에 개인 블로그 이름 추가되야함) -->
  <v-container>
    <Navbar />
    <v-row>
      <!-- 왼쪽 사이드바(좋아요, 공유버튼) -->
      <v-col cols="1" class="pa-0"></v-col>
      <v-col class="pa-0">  
        <v-layout column="12">
        <!-- 게시글 제목 -->
          <div class="title-area">
            <h1>{{ $route.params.title }}</h1>
            <h6>작성자: {{ $route.params.username }}</h6>
          </div>
        
        <!-- TextEditor 미리보기만(마크다운) -->
        <div class="col-lg-12">
          <Viewer :initialValue="this.content"/>
        </div>
        <!-- 게시글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->

        <!-- 블로그 작성자 Thumnail -->
        <!-- 짧은 자기 소개 -->
        <!-- 설정한 SNS주소 있으면 나오게(이미지 버튼으로) -->

        <!-- 다음글 이전글 버튼-->

        <!-- 댓글 작성창 -->
        <div class="col-lg-9">          
          <v-textarea
            clearable
            label="댓글 작성"
            placeholder="댓글을 작성하세요"
            v-model="CommentInput"
          ></v-textarea>
          <v-btn
            @click="CommentCreate"
          >댓글 작성
          </v-btn>
        </div>
        <!-- 댓글 -->
        <h1>댓글창</h1>
        <div v-for="comment in Comments" :key="comment.username" class="col-lg-12">
          <p>작성자: {{ comment.author }} 내용:{{ comment.content }}</p>
        </div>
        <!-- 댓글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
        </v-layout>
      </v-col>
      <!-- 오른쪽 사이드바 -->
      <v-col id="side" class="hidden-sm-and-down pa-0" cols="3">
        <h3> 게시글 목차 </h3>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { Viewer } from '@toast-ui/vue-editor'
import Navbar from './Navbar.vue'

import axios from 'axios'

const storage = window.sessionStorage
const API_URL = 'http://i3a507.p.ssafy.io:8081/'

export default {
    name: 'postdetail',
    components: {
      Viewer,
      Navbar
    },
    data() {
        return {
          content: '',
          Comments: this.$route.params.data.comments,
          CommentInput: '',
        }
    },
    mounted() {
    },
    created(){
      this.fetchData()
    },
    methods: {
      fetchData(){
        if (this.$route.params.content) {
          this.content = this.$route.params.content
        }
      },
      CommentCreate() {
        console.log('TRY create')
        if (storage.getItem("login_user")) {
          const tmp_comment = {
            author: storage.getItem("login_user"),
            content: this.CommentInput,
            uid: storage.getItem("uid"),
            pid: this.$route.params.data.pid
          } 
          axios.post(API_URL + 'api/v3/', tmp_comment)
            .then(() => this.Comments.push(tmp_comment))
            .catch(err => console.log(err))
        } else {
          alert('로그인한 유저만 댓글을 달 수 있습니다.')
        }
      }
    }
}
</script>

<style scoped>
#side {
  margin-left:2vw;
  position: sticky;
  top : 130px;
}
</style>