<template>
  <div id="#app">
    <!-- 네비게이션 바 -->
    <Navbar></Navbar>
    <!-- 유효성 검사 추가해야함 -->
    <v-app-bar
      fixed
      color="white"
			height="90px"
			justify
      flat
    >
      <h1 class="logo_title"><router-link to="/">DO!</router-link></h1>
			<v-spacer></v-spacer>
      <div class="login">
				<span><router-link to="/register">회원가입</router-link></span>
      </div> 
    </v-app-bar>
    <!-- navbar end -->

    <v-container fluid fill-height>
      <div class="login_do">
        <span><h2>DO!에 로그인하세요</h2></span>
        <v-form name="form" @submit.prevent style="width:318px">
          <v-text-field
            outlined
            placeholder="email"
            hide-details
            name="email"
            v-model="email"
          >
          </v-text-field>
          <v-text-field
            @keyup.enter="login"
            type="password"
            outlined
            placeholder="password"
            hide-details 
            sytle="top:-1px;"
            name="password"
            v-model="password"
          >
          </v-text-field>
          
          <div class="login_append">
            <v-checkbox
              dense
              label="로그인 상태 유지"
              color="#6e8af8"
            ></v-checkbox>
            <span>아이디/비밀번호 찾기</span> 
          </div>
          <div class="login_btn" @click="login">
            <v-btn 
              block 
              depressed 
              color="#6e8af8"
              height="48px"
              class="white--text"
            >
              로그인
            </v-btn>
          </div>
        </v-form>
        <!-- 소셜 로그인 단 -->
        <div class="google mt-2">
          <v-btn 
            block 
            depressed 
            color="#ff7f00"
            height="48px"
            class="white--text"
          >
            <GoogleLogin class="big-button" :renderParams="renderParams" :params="params" :onSuccess="onSuccess" :onFailure="onFailure"> Google Login</GoogleLogin>
          </v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
import GoogleLogin from 'vue-google-login';

const storage = window.sessionStorage
// const API_URL = 'http://localhost:8081/'
const API_URL = 'http://i3a507.p.ssafy.io:8081/'

export default {
  name: 'login',
  components: {
    Navbar,
    GoogleLogin
  },
  computed: {
  },
  data() {
    // user: new User('', ''),
    return {
      isLogin: this.$store.state.isLoggedIn,
      email: "",
      password: "",
      loading: false,
      message: "",
      renderComponent: true,
      params :{
        client_id : '464615746303-dj9932cqlcukr92o1ig3eci1ubc5vlui.apps.googleusercontent.com',
        scope : 'email profile'
      },
    }
  },
  methods: {
    login() {
      // LOGIN 변이 실행 후 로그인 체크
      const user = {
        email: this.email,
        password: this.password
      }
      this.$store.dispatch('login', user)
        .then(() => {
          this.isLogin = this.$store.getters.isAuthenticated
          if (this.isLogin) {
            this.$router.push('/')
          }
        })
      .catch(err => console.log(err))
    },
    
    // 로그인 했는지 체크
    init() {
      // 스토리지에 토큰 있으면
      if (storage.getItem("jwt-auth-toekn")) {
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다."
      } else {
        storage.setItem("jwt-auth-token", "");
      }
    },
    onSuccess(googleUser){
      storage.setItem("jwt-auth-token", "")
      storage.setItem("login_user", "")
      storage.setItem("uid", "")
      storage.setItem("google_login", "")
      let id_token = googleUser.wc.id_token;
      const config = {
        headers: {Authorization: `Bearer ${id_token}`}
      }
      axios.post(API_URL + 'googlelogin', null, config)
      .then(res => {
        console.log(res)
        storage.setItem("jwt-auth-token", res.data["jwt-auth-token"])
        storage.setItem("login_user", res.data.request_body.email)
        storage.setItem("uid", res.data.request_body.uid)
        storage.setItem("google_login", true)
        this.$router.push('/')
      })
      // .catch((err) => console.log(err))
    }
  },
  created() {
    this.isLogin = this.$store.state.isLoggedIn
  },
  mounted() {
    this.init()
    // 로그인한 상태로 로그인 페이지에 진입하면 홈으로 돌려보냄
    if (storage.getItem("jwt-auth-toekn")) {
      this.$router.push('/')
    }
  },
}
</script>

<style scoped>
header {
  background-color:transparent;
}

.container {
  padding-top:120px; 
}
/* login 영역 화면 가운데에 고정 어떻게 하지? */
.login_do{
  text-align: center;
  margin : 120px auto;
}
.login_do > span{
  display : inline-block;
  margin-bottom : 30px;
}
.v-text-field{
  border-radius: 0;
}
.login_btn{
  margin : 20px 0 0 0;
}

.v-input{
  margin : 0;
}

.login_append {
  margin : 12px 0 0 0;
  font-size : 13px;
}

.login_append > .v-input {
  display: inline-block;
  /* float: left; */
}

.login_append > span {
  float:right;
  padding-top:7px;
}
.v-input >>> label {
  font-size: 13px;
}

/* navbar css */
@import url('https://rsms.me/inter/inter.css');
@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);

header{
	padding : 0 10%;
}
h1{
	font-size : 2em;
	font-family: 'Inter', sans-serif;
}
a{
  text-decoration: none;
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
	width: 60px;
	margin: 0 70px 0 0;
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
	border-left: 1px solid lightgray;
	margin : 0px 0px 0px 20px ;
	padding: 0px 0px 0px 20px;
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
</style>