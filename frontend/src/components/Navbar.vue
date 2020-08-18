<template>
  <v-app-bar
      fixed
      color="white"
      elevate-on-scroll
			height="90px"
			justify
  >
    <h1 class="logo_title"><router-link to="/">DO!</router-link></h1>
    <v-toolbar-items style="align-items: center">
      <router-link to="/introduce">소개</router-link>
    </v-toolbar-items>
    <v-toolbar-items style="align-items: center">
      <router-link to="/feed">피드</router-link>
    </v-toolbar-items>
    <v-toolbar-items style="align-items: center">
      포럼
    </v-toolbar-items>
      <div class="notice">
        <v-icon small>fas fa-volume-down</v-icon>
        <span>{{noticemsg}}</span>
			</div>
			<v-spacer></v-spacer>
      <router-link to="/search">
      <v-icon small color="black" class="icon" style="margin-top:5px;">fas fa-search</v-icon>
      </router-link>
      <!-- 로그인 안했을 때 -->
      <div v-if="!isLogin" class="login">
				<span><router-link to="/login">로그인</router-link></span>
        <span>/</span>
        <span><router-link to="/register">회원가입</router-link></span>
      </div> 
      <!-- 로그인 했을때 -->
      <!-- <div v-if="isLogin" class="info_blog">
        <v-icon small color="black" class="icon">fas fa-bell</v-icon>
      </div> -->
      <div v-if="isLogin" class="newpost">
        <router-link to="/texteditor"><v-btn rounded outlined depressed color="#6e8af8">새 글 작성</v-btn></router-link>
      </div>
      <div v-if="isLogin" class="info_blog"> 
        <div @click="profileshow">
          <img src="https://item.kakaocdn.net/do/437998cb670b87a9a8faca156155beeb8f324a0b9c48f77dbce3a43bd11ce785" class="thumb_profile" alt>
        </div>
        <!--프로필 클릭시 팝업되는 드롭다운 메뉴-->
        <v-card class="shortcuts">
          <v-list v-if="profile">
            무림고수
            <v-list-item>
              내블로그 
            </v-list-item>
            <v-list-item>
              <router-link to="/setting">설정</router-link>
            </v-list-item>
            <v-list-item v-if="!google_login" @click="onClickLogout">
              로그아웃
            </v-list-item>
            <v-list-item v-if="google_login" @click="onClickLogout">
              <GoogleLogout :params="params" :logoutButton=true>로그아웃</GoogleLogout>
            </v-list-item>
          </v-list>
        </v-card>
      </div>
      <div v-if="isLogin" class="info_blog" @click="profileshow"> 
        <v-icon small color="black" style="margin: 20px 4px 0px 0px">fas fa-angle-down</v-icon>
      </div>
  </v-app-bar>
</template>

<script>
const storage = window.sessionStorage
import GoogleLogin from 'vue-google-login' 

export default {
  name: 'Navbar',
  component: {
    GoogleLogout: GoogleLogin,
  },
  data() {
    return {
      profile: false,
      isLogin: this.$store.state.isLoggedIn,
      noticemsg: '공지입니다.',
      google_login: storage.getItem("google_login"),
    }
  },
  computed: {
    // 현재 로그인 한 상태인지 아닌지 체크
  },
  methods: {
    onClickLogout() {
      // LOGOUT 변이 실행 후 리다이렉트
      this.$store.dispatch('logout')
      this.isLogin = this.$store.getters.isAuthenticated
    },
    loginChecker() {
      this.$store.commit('logincheck')
      this.isLogin = this.$store.getters.isAuthenticated
    },
    forceRender() {
      this.renderComponent = false
      this.$nextTick(() => {
        this.renderComponent = true
      })
    },
    profileshow() {
      this.profile = !this.profile
    }
  },
  created() {
    this.loginChecker()
  },
  // computed() {
  //   this.loginChecker()
  // }
  // watch: {
  //   isLogin (val, prev) {
  //     if (val !== prev) {
  //       val = this.$store.getters.isAuthenticated
  //     }
  //   
  // }
}
</script>

<style scoped>
@import url('https://rsms.me/inter/inter.css');
@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);

header{
	padding : 0 4rem;
  min-width:1100px;
}
h1{
	font-size : 2em;
	font-family: 'Inter', sans-serif;
  font-weight: 800;
}
a{
  color : black !important;
  text-decoration: none !important;
}
button > span {
  font-family: 'Noto Sans DemiLight', 'sans-serif';
  font-size: 13px;
}
div > .newpost > a >button{
  font-family: 'Noto Sans DemiLight', 'sans-serif';
  font-size: 13px;
}
div > .newpost > a >button:hover {
  background-color:#6e8af8;
  /* color:white !important;  */
}
.logo_title{
	display : inline-block;
	margin: 0 43px 0 0;
	vertical-align: top;
}
.v-toolbar__items{
	font-size: 16px;
	font-family: 'Noto Sans KR', 'sans-serif';
	font-weight: 400;
	/* margin: 0 40px 0 0; */
}
.login span a{
	color: black;
	font-size: 13px;
	font-family: 'Noto Sans DemiLight', 'sans-serif';
	text-decoration: none;
}
.notice {
  margin : 0px 0px 0px 54px ;
	padding: 0px 0px 0px 0px;
}
.notice::before {
  content:'|';
  padding-right: 24px;
}
.notice i {
	color : black;
}
.notice span {
	margin-left: 10px;
	font-size: 13px;
	font-family: 'Noto Sans DemiLight', 'sans-serif';
}
.icon{
  display: inline-block;
  margin: 0px 31px 0px 0px;
}
.thumb_profile{
  display: inline-block;
  width:50px;
  height:50px;
  border-radius:50%;
}
.shortcuts{
  position : fixed;
  top: 80px;
  width: 150px;
  right: 10%;
  background-color: aqua;
}
.newpost {
  margin-right:20px;
}
.v-toolbar__items{
  padding: 0 7px 0 17px;
}
</style>