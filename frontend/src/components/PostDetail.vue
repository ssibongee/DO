<template>
  <!-- navbar(로고에 개인 블로그 이름 추가되야함) -->
  <div class="container">
    <div class="row">
      <!-- 왼쪽 사이드바(좋아요, 공유버튼) -->
      <div class="col-lg-9 row">
        
        <!-- 제목 -->
        <div class="col-lg-12">
          <h2>{{ $route.params.title }}</h2>
          <h6>작성자: {{ $route.params.username }}</h6>
        </div>
        <!-- TextEditor 미리보기만(마크다운) -->
        <div class="col-lg-12">
          <Viewer :initialValue="this.content"/>
        </div>
        <!-- 블로그 작성자 Thumnail -->
        
        <!-- 짧은 자기 소개 -->
        
        <!-- 설정한 SNS주소 있으면 나오게 -->

        <!-- 다음글 이전글 버튼-->

        <!-- 댓글 작성창 -->
        <div class="col-lg-9">          
          <v-textarea
            clearable
            label="댓글 작성"
            placeholder="댓글을 작성하세요"
            :value="CommentInput"
          ></v-textarea><!-- <v-textarea
            clearable
            clear-icon="지우기"
            label="댓글 작성"
            placeholder="댓글을 작성하세요"
            :value="CommentInput"
          ></v-textarea> -->
        </div>
        <div class="col-lg-3">
          <button>작성 완료</button>
        </div>
        <!-- 댓글 -->
        <h1>댓글창</h1>
        <div v-for="comment in Comments" :key="comment.username" class="col-lg-12">
          <p>작성자: {{ comment.username }} 내용:{{ comment.content }}</p>
        </div>
      </div>
      <!-- 오른쪽 사이드바 -->
      <div class="d-none d-lg-block d-xl-block col-3">
        <Sidebar></Sidebar>
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from './Sidebar.vue'
import { Viewer } from '@toast-ui/vue-editor'

export default {
    name: 'postdetail',
    components: {
      Sidebar,
      Viewer,
    },
    data() {
        return {
          Comments:[
            {
              username: '우주호',
              content: 'hello'
            },
            {
              username: '안시원',
              content: 'hihi'
            },
          ],
          CommentInput: null,
          content: 'hello everyone'
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
      }
    }
}
</script>

<style>

</style>