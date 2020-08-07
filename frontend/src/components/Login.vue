<template>
  <div id="#app">
    <!-- 네비게이션 바 -->
    <Navbar></Navbar>
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
      </div>
    </v-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
import User from '../models/user'

const storage = window.sessionStorage
// const API_URL = 'http://localhost:8081/'
const API_URL = 'http://i3a507.p.ssafy.io:8081/'

export default {
  name: 'login',
  components: {
    Navbar,
  },
  computed: {
  },
  data() {
    return {
      user: new User('', ''),
      email: "",
      password: "",
      loading: false,
      message: "",
      renderComponent: true,
    }
  },
  methods: {
    login() {
      // this.loading = true
      // this.$validator.validateAll()

      // if (this.errors.any()) {
      //   this.loading = false
      //   return
      // }
      console.log('TRY login')
      if (this.email && this.password) {
        storage.setItem("jwt-auth-token", "")
        storage.setItem("login_user", "")
        axios.post(API_URL+'api/signin', {
          email: this.email,
          password: this.password
        }).then(res => {
            if (res.data.status) {
              this.message = res.data.request_body.email + "로 로그인 되었습니다."
              storage.setItem("jwt-auth-token", res.data["jwt-auth-token"])
              storage.setItem("login_user", res.data.request_body.email)
              this.$router.push('/')
            } else {
              this.message = "로그인해주세요."
              alert("입력 정보를 확인해주세요.")
            }
          })
          .catch(err => console.log(err))
      }
    },
    // logout() {
    //   storage.setItem("jwt-auth-token", "")
    //   storage.setItem("login_user", "")
    //   this.message = "로그인해주세요."
    // },
    
    // 로그인 했는지 체크
    init() {
      // 스토리지에 토큰 있으면
      if (storage.getItem("jwt-auth-toekn")) {
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다."
      } else {
        
        storage.setItem("jwt-auth-token", "");
      }
    },
    forceRender() {
      this.renderComponent = false
      this.$nextTick(() => {
        this.renderComponent = true
      })
    },
    test() {
      console.log('button OK')
    }
  },
  mounted() {
    this.init()
    // 로그인한 상태로 로그인 페이지에 진입하면 홈으로 돌려보냄
    if (this.loggedIn) {
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
  float: left;
}

.login_append > span {
  float:right;
  padding-top:7px;
}
.v-input >>> label {
  font-size: 13px;
}
</style>