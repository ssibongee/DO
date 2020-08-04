<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <form name="form" @submit.prevent="login">
        <div class="form-group">
          <label for="username">email</label>
          <input
            type="email"
            class="form-control"
            name="email"
            v-model="email"
            v-validate="'required'"
          />
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors.has('username')"
          >Username is required!</div>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            class="form-control"
            name="password"
            v-model="password"
            v-validate="'required'"
          />
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors.has('password')"
          >Password is required!</div>
        </div>
        <div class="form-group">
          <button class="btn btn-primary btn-block" :disabled="loading">
            <span class="spinner-border spinner-border-sm" v-show="loading"></span>
            <span>Login</span>
          </button>
        </div>
        <div class="form-group">
          <div class="alert alert-danger" role="alert" v-if="message">{{message}}</div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import User from '../models/user'

const storage = window.sessionStorage
const API_URL = 'http://localhost:8399/'

export default {
  name: 'login',
  computed: {
  },
  data() {
    return {
      user: new User('', ''),
      email: "",
      password: "",
      loading: false,
      message: "",
      status: "",
      token: "",
      info: ""
    }
  },
  methods: {
    setInfo(status, token, info) {
      this.status = status
      this.token = token
      this.info = info
    },
    login() {
      storage.setItem("jwt-auth-token", "")
      storage.setItem("login_user", "")
      axios.post(API_URL+'api/signin', {
        email: this.email,
        password: this.password
      }).then(res => {
          if (res.data.status) {
            console.log(res.headers)
            this.message = res.data.request_body.email + "로 로그인 되었습니다."
            console.dir(res.headers["jwt-auth-token"])
            this.setInfo(
              "성공",
              res.headers["jwt-auth-token"],
              JSON.stringify(res.data.request_body)
            )
            storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"])
            storage.setItem("login_user", res.data.request_body.email)
            this.$router.push('/')
          } else {
            this.setInfo("", "", "")
            this.message = "로그인해주세요."
            alert("입력 정보를 확인해주세요.")
          }
        })
        .catch(err => {
            this.setInfo("실패", "", JSON.stringify(err.response || err.message))
        })
    },
    logout() {
      storage.setItem("jwt-auth-token", "")
      storage.setItem("login_user", "")
      this.message = "로그인해주세요."
      this.setInfo("로그아웃 성공", "", "")
    },
    
    // 로그인 했는지 체크
    init() {
      // 스토리지에 토큰 있으면
      if (storage.getItem("jwt-auth-toekn")) {
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다."
      } else {
        // 토큰 없으면 제대로 로그인 접근한 사람이 아님
        storage.setItem("jwt-auth-token", "");
      }
    },
    // handleLogin() {
    //   this.loading = true
    //   this.$validator.validateAll()

    //   if (this.errors.any()) {
    //     this.loading = false
    //     return
    //   }

    //   if (this.user.email && this.user.password) {
    //     this.$store.dispatch('login', this.user)
    //       .then(() => {
    //         // 로그인 성공하면 홈으로 보냄
    //         this.$router.push('/')
    //       })
    //       .catch(err => console.log(err))
    //   }
    // },
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
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>