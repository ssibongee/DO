<template>
  <!-- navbar(로고에 개인 블로그 이름 추가되야함) -->
  <v-container>
    <Navbar />
    <v-row>
      <!-- 왼쪽 사이드바(좋아요, 공유버튼) -->
      <v-col cols="1" class="pa-0">

      </v-col>
      <!-- 게시글, 댓글  -->
      <v-col class="pa-0">  
        <v-layout column="12">
          <!-- 게시글 제목 -->
          <div class="title-area">
            <h1>글 제목 : {{ post.title }}</h1>
            <h6>작성자: {{ post.author }}</h6>
          </div>

          <!-- TextEditor 미리보기만(마크다운) -->
          <div class="col-lg-12">
            <Viewer :initialValue="this.content"/>
          </div>
          <!-- 게시글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
          <div></div>
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
              @click="onCommentCreate"
            >댓글 작성
            </v-btn>
          </div>
          <!-- 댓글 목록 -->
          <h1>댓글창</h1>
          <!-- 댓글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
          <div v-for="comment in Comments" :key="comment.username" class="col-lg-12">
            <p>작성자: {{ comment.author }} 내용:{{ comment.content }}</p>
            <v-btn 
              v-if="comment.isauthor"
              @click="onCommentDelete(comment)"
            >
            삭제</v-btn>
            <v-btn 
              v-if="comment.isauthor"
              @click="onCommentUpdate(comment)"
            >
            수정</v-btn>
          </div>
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
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
    name: 'postdetail',
    components: {
      Viewer,
      Navbar
    },
    data() {
        return {
          post: {
            title: '',
            author: '',
            content: '',
          },
          content: this.$route.params.data.tmp,
          Comments: null,
          CommentInput: '',
        }
    },
    created(){
      axios.get(API_URL + 'api/v2/p/' + storage.getItem("pid"))
        .then(res => {
          // this.post = res.data
          this.post.title = res.data.title
          this.post.author = res.data.author
          this.post.content = res.data.content
          console.log(this.post)
          this.Comments = res.data.comments
          this.isCommentauthor(this.Comments)
          this.isPostauthor(this.isPostauthor)
      })
    },
    mounted() {
    },
    // computed() {
    // },
    methods: {
      fetchData() {
        axios.get(API_URL + 'api/v2/p/' + storage.getItem("pid"))
          .then(res => {
            this.post = res.data
            console.log(this.post)
            this.Comments = res.data.comments
            this.isCommentauthor(this.Comments)
            this.isPostauthor(this.isPostauthor)
          })
      },
      // 글 작성자인지 확인
      isPostauthor() {
        if (this.post.author && storage.getItem("login_user") && this.post.uid && storage.getItem("uid")) {
          return true
        } else {
          return false
        }
      },
      // 댓글 Create 메서드
      onCommentCreate() {
        if (storage.getItem("login_user")) {
          const tmp_comment = {
            author: storage.getItem("login_user"),
            content: this.CommentInput,
            uid: storage.getItem("uid"),
            pid: storage.getItem("pid")
          }
          // 서버에 댓글 작성 요청 보냄
          axios.post(API_URL + 'api/v3/', tmp_comment)
            .then(() => {
              // 댓글 작성 완료 후 새로 댓글 받아옴
              axios.get(API_URL+'api/v3/'+storage.getItem("pid"))
                .then(res => this.Comments = this.isCommentauthor(res.data))
              this.CommentInput = ""
            })
        } else {
          alert('로그인한 유저만 댓글을 달 수 있습니다.')
        }
      },
      // 작성자 비교 후 삭제 버튼 flag 설정하는 메서드
      isCommentauthor(Comments) {
        Comments.forEach(one_comment => {
          if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
            one_comment.isauthor = true
          } else {
            one_comment.isauthor = false
          }
        })
        return Comments
      },
      // 댓글 Delete 메서드
      onCommentDelete(one_comment) {
        if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
          this.Comments.splice(this.Comments.indexOf(one_comment),1)
          axios.delete(API_URL + `api/v3/${one_comment.cid}`)
            .then(() => {})
        } else {
          alert('비정상적인 접근입니다')
        }
      },
      onCommentUpdate(one_comment) {
        console.log("수정 버튼", one_comment)
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