<template>
  <div id="app">
  <!-- navbar(로고에 개인 블로그 이름 추가되야함) -->
  <Navbar/>
  <v-container class="post-box">
    <v-row>
      <!-- 왼쪽 사이드바(좋아요, 공유버튼) -->
      <v-col cols="1" class="pa-0">
      <v-btn icon depressed small @click="addToFeed" color="pink">
        <v-icon :class="like"></v-icon>
      </v-btn>
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
        <div v-for="comment in Comments" :key="comment.cid" class="col-lg-12">
          <div>
            <Comment 
              :comment="comment"
              @Click-Delete-Btn="CommentRead"
              @Click-Update-Btn="CommentRead"
              @Child-Create="CommentRead"
            />
          </div>
        </div>
        </v-layout>
      </v-col>

      <!-- 오른쪽 사이드바 -->
      <v-col id="side" class="hidden-sm-and-down pa-0" cols="3">
        <h3> 게시글 목차 </h3>
      </v-col>
    </v-row>
  </v-container>
  </div>
</template>

<script>
import Navbar from './Navbar.vue'
import Comment from './Comment.vue'

import { Viewer } from '@toast-ui/vue-editor'
import axios from 'axios'

const storage = window.sessionStorage
const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'

export default {
    name: 'postdetail',
    components: {
      Viewer,
      Navbar,
      Comment,
    },
    data() {
      return {
        post: {
          title: '',
          author: '',
          content: '',
          islike: this.$route.params.data.isLike,
        },
        content: this.$route.params.data.tmp,
        Comments: null,
        CommentInput: '',
        ChildFlag: false,
        ChildCommentInput: '',
        FeedFlag: '',
        like: '',
      }
    },
    created(){
      console.log(storage.getItem("uid"))
      axios.post(API_URL + 'api/v2/p/',{
        // headers :{
        //   'Content-Type': 'application/json',
        //   'Accept':'application/json'
        // },
        uid : storage.getItem("uid"),
        pid : storage.getItem("pid")
      })
        .then(res => {
          // console.log(res)
          // this.post = res.data
          this.post.title = res.data.title
          this.post.author = res.data.author
          this.post.content = res.data.content
          this.post.islike = res.data.isLike
          // console.log(this.post)
          if(this.post.islike===true){
            this.like = "fas fa-heart"
            this.FeedFlag= true
          } else {
            this.like = "far fa-heart"
            this.FeedFlag = false
          }
          // console.log(this.post)
          this.Comments = res.data.comments
          // console.log(this.Comments)
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
        axios.post(API_URL + 'api/v2/p',{
          uid : storage.getItem("uid"),
          pid : storage.getItem("pid")
        })
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
              this.CommentRead()
            })
        } else {
          alert('로그인한 유저만 댓글을 달 수 있습니다.')
        }
      },
      // 해당 Post의 모든 Comment 읽어오기 & 인풋 초기화
      CommentRead() {
        axios.get(API_URL+'api/v3/'+storage.getItem("pid"))
          .then(res => {
            this.Comments = this.isCommentauthor(res.data)
          })
        this.CommentInput = ""
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
      //좋아요에 포스트를 담거나 빼는 메서드
      addToFeed(){
        //로그인 체크
        if(storage.getItem("login_user")){
          //이미 피드에 있는지 체크
          if(this.FeedFlag===false){
            this.FeedFlag=true;
            this.post.islike=true;
            this.like="fas fa-heart"
            axios
            .put(API_URL+'api/v2/likes',{
              uid : storage.getItem("uid"),
              pid : storage.getItem("pid"),
              status : this.FeedFlag,
            })
            .then(function(response){
              console.log(response);
              })
            .catch(function(error){
              console.log(error);
            })
          }
          //삭제하면 db에서 제거
          else {
            this.like="far fa-heart"
            this.post.islike=false;
            this.FeedFlag=false;
            axios.put(API_URL+'api/v2/likes',{
              uid : storage.getItem("uid"),
              pid : storage.getItem("pid"),
              status : this.FeedFlag,
            })
            .then(function (response){
						console.log(response);
            })
            .catch(function(error){
              console.log(error);
            });
          }
        }
        else {
          alert('로그인 후 이용해주세요.')
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
.test {
  display: flex
}
.post-box {
  margin-top: 130px;
}

</style>