<template>
  <div id="app">
  <!-- navbar(로고에 개인 블로그 이름 추가되야함) -->
    <Navbar/>
    <v-container class="post-box">
      <!-- {{ this.post }} -->
      <v-row>
        <!-- 왼쪽 사이드바(좋아요, 공유버튼) -->
        <v-col cols="1" class="pa-0">
        <v-btn icon depressed small @click="addToFeed" color="pink">
          <v-icon :class="like"></v-icon>
        </v-btn>
        </v-col>
        <!-- 게시글, 댓글 큰틀  -->
        <v-col cols="10" class="pa-0">  
          <v-layout column="12">
            <!-- 게시글 헤더 -->
            <h1 class="post-title">{{ post.title }}</h1>
            <v-row justify="center">
              <v-col cols="2">
                <v-img class="author_profile_img" v-if="post.authorInfo.profileImage" :src="post.authorInfo.profileImage"></v-img>
              </v-col>
              <v-col cols="10">
                <h4 class="post-author">{{ post.author }} </h4>
              </v-col>
            </v-row>  
            
            <PostTagBox
              :tagList="post.tag"
            />
            <!-- TextEditor 미리보기만(마크다운) -->
            <v-col cols="12" class="post-content">
              <!-- <Viewer :initialValue="this.post.content"/> -->
              <Viewer 
                :initialValue="this.content"
                @change="onEditorChange"
              />
            </v-col>
            <!-- 게시글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
            <v-col v-if="post.isauthor" cols="12">
              <v-btn @click="onPostUpdateBtn" color="success">게시글 수정</v-btn>
              <v-btn @click="onPostDeleteBtn" color="primary">게시글 삭제</v-btn>
            </v-col>
            <!-- 블로그 작성자 Thumnail -->

            <!-- 작성자 Thumnail, 자기 소개, 후원버튼 & 후원QR -->
            <v-row>
              <v-col cols="3">
                <v-img class="author_profile_img" v-if="post.authorInfo.profileImage" :src="post.authorInfo.profileImage"></v-img>
                <p v-else>{{ post.author }}</p>
              </v-col>
              <v-col cols="4" v-if="post.authorInfo.introduce">
                {{ post.authorInfo.introduce }}
              </v-col>
              <v-col cols="3" v-else>
                안녕하세요, {{ post.author }}입니다.
              </v-col>
              <v-col v-if="post.authorInfo.qrImage" cols="5">
                <v-btn @click="ClickQrBtn" v-if="!qrflag">글쓴이에게 후원하기</v-btn>
                <div class="qrimage-resize">
                  <v-img v-if="qrflag" :src="post.authorInfo.qrImage"></v-img>
                </div>
              </v-col>
              <v-col v-else cols="5"></v-col> 
            </v-row>
            <!-- 소셜 로그인 버튼 -->
            <v-row class="social-login">
              <i v-if="post.authorInfo.github" class="fab fa-github fa-3x" href=""></i>
              <i v-if="post.authorInfo.instagram" class="fab fa-instagram fa-3x" href=""></i>
              <i v-if="post.authorInfo.facebook" class="fab fa-facebook fa-3x" href=""></i>
            </v-row>
            <h3 class="comment-header">댓글</h3>
            <!-- 댓글 작성창 -->
            <div class="col-lg-12 mb-2">          
              <v-textarea
                clearable
                label="댓글 내용을 작성하세요"
                rows="3"
                v-model="CommentInput"
                solo
              ></v-textarea>
              <v-btn
                @click="onCommentCreate"
              >댓글 작성
              </v-btn>
            </div>
            <!-- 댓글 목록 -->
            <!-- 댓글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
            <div v-if="renderComponent">
              <div v-for="comment in Comments" :key="comment.cid" class="col-lg-12">
                <Comment 
                  :comment="comment"
                  @Click-Delete-Btn="CommentRead"
                  @Click-Update-Btn="CommentRead"
                  @Child-Create="CommentRead"
                  @Click-Child-Change-Btn="CommentRead"
                />
              </div>
            </div>
          </v-layout>
        </v-col>
        <v-col cols="1"></v-col>
        <!-- 오른쪽 사이드바 -->
        <!-- <v-col id="side" class="hidden-sm-and-down pa-0" cols="3">
          <h3> 게시글 목차 </h3>
        </v-col> -->
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Navbar from './Navbar.vue'
import Comment from './Comment.vue'
import PostTagBox from './PostTagBox.vue'

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
      PostTagBox,
    },
    data() {
        return {
          post: {
            title: '',
            author: '',
            content: '',
            islike: this.$route.params.data.isLike,
          },
          content: this.$route.params.data.content,
          Comments: null,
          CommentInput: '',
          ChildCommentInput: '',
          FeedFlag: '',
          like: '',
          renderComponent: true,
          qrflag: false,
        }
    },
    created(){
      axios.post(API_URL + 'api/v2/p/',{
        // headers :{
        //   'Content-Type': 'application/json',
        //   'Accept':'application/json'
        // },
        uid : storage.getItem("uid"),
        pid : storage.getItem("pid")
      })
        .then(res => {
          this.post = res.data
          this.post.title = res.data.title
          this.post.author = res.data.author
          this.post.content = res.data.content
          this.post.islike = res.data.isLike
          if(this.post.islike===true){
            this.like = "fas fa-heart"
            this.FeedFlag= true
          } else {
            this.like = "far fa-heart"
            this.FeedFlag = false
          }
          this.Comments = res.data.comments
          this.isCommentauthor(this.Comments)
          this.post.isauthor = this.isPostauthor()
      })
    },
    mounted() {
      this.fetchData()
    },
    methods: {
      forceRerender() {
        this.renderComponent = false;
        this.$nextTick(() => {
          this.renderComponent = true;
        })
      },
      fetchData() {
        axios.post(API_URL + 'api/v2/p/',{
          uid : storage.getItem("uid"),
          pid : storage.getItem("pid")
        })
          .then(res => {
            this.post = res.data
            this.Comments = res.data.comments
            this.isCommentauthor(this.Comments)
            this.post.isauthor = this.isPostauthor()
          })
      },
      onEditorChange() {
        this.fetchData()
      },
      // 글 작성자인지 확인
      isPostauthor() {
        if (this.post.author === storage.getItem("login_user") && this.post.uid === Number(storage.getItem("uid"))) {
          return true
        } else {
          return false
        }
      },
      // 게시글 수정 페이지로 보내는 버튼
      onPostUpdateBtn() {
        if (this.isPostauthor()) {
          console.log()
          // this.$router.push({path:`/${}/${}`})
        }
      },

      // 게시글 삭제 버튼
      onPostDeleteBtn() {
        if (this.isPostauthor()) {
          axios.delete(API_URL + 'api/v2/' + this.post.pid)
            .then(() => {
              alert('게시글이 삭제되었습니다.')
              this.$router.push('/')
          })
        }
      },
      
      // 댓글 Create 메서드
      onCommentCreate() {
        if (storage.getItem("login_user")) {
          if (this.CommentInput.trim()) {
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
            alert('빈 댓글은 작성할 수 없습니다.')
          }
        } else {
          alert('로그인한 유저만 댓글을 달 수 있습니다.')
        }
      },
      // 해당 Post의 모든 Comment 읽어오기 & 인풋 초기화
      CommentRead() {
        axios.get(API_URL+'api/v3/'+storage.getItem("pid"))
          .then(res => {
            this.Comments = this.isCommentauthor(res.data)
            this.forceRerender()
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
          if (one_comment.child.length) {
            one_comment["child"].forEach(one_child => {
              if (one_child.author === storage.getItem("login_user") && one_child.uid === Number(storage.getItem("uid"))) {
                one_child.isauthor = true
              } else {
                one_child.isauthor = false
              }
            })
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
      },
      ClickQrBtn() {
        this.qrflag = !this.qrflag
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
.social-login{
  margin: 1rem 0;
}
.post-title {
  margin: 0 0 2rem 0;
  border-bottom: 1px solid black;
}
.post-content {
  margin: 2rem 0 2rem 0;
  padding: 0 0 1rem 0;
  border-bottom: 1.5px solid #888888
}
.comment-header {
  margin: 2rem 0 2rem 0;
  padding: 0 0 1rem 0;
  border-bottom: 1.5px solid #888888
}
.qrimage-resize {
  width: 10rem;
  height: 15rem;
}
.author_profile_img {
  border-radius: 50%;
} 
</style>