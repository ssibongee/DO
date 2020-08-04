<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <form name="form" @submit.prevent="handleLogin">
        <div class="form-group">
<<<<<<< HEAD
          <label for="username">Username</label>
          <input
            type="text"
            class="form-control"
            name="username"
            v-model="user.username"
=======
          <label for="username">email</label>
          <input
            type="email"
            class="form-control"
            name="email"
            v-model="user.email"
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
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
            v-model="user.password"
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
import User from '../models/user'

<<<<<<< HEAD
export default {
  name: 'login',
  computed: {
    loggedIn() {
      return this.$store.state.status.loggedIn
    }
=======
const storage = window.sessionStorage

export default {
  name: 'login',
  computed: {
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
  },
  data() {
    return {
      user: new User('', ''),
      loading: false,
      message: ''
    }
  },
  mounted() {
<<<<<<< HEAD
=======
    this.init()
    // 로그인한 상태로 로그인 페이지에 진입하면 홈으로 돌려보냄
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
    if (this.loggedIn) {
      this.$router.push('/')
    }
  },
  methods: {
    handleLogin() {
      this.loading = true
      this.$validator.validateAll()

      if (this.errors.any()) {
        this.loading = false
        return
      }

<<<<<<< HEAD
      if (this.user.username && this.user.password) {
        this.$store.dispatch('login', this.user).then(
          () => {
            this.$router.push('/profile')
          },
          error => {
            this.loading = false
            this.message = error.message
          }
        )
=======
      if (this.user.email && this.user.password) {
        this.$store.dispatch('login', this.user)
          .then(() => {
            // 로그인 성공하면 홈으로 보냄
            this.$router.push('/')
          })
          .catch(err => console.log(err))
      }
    },
    
    // 로그인 했는지 체크
    init() {
      // 스토리지에 토큰 있으면
      if (storage.getItem("jwt-auth-toekn")) {
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다."
      } else {
        // 토큰 없으면 제대로 로그인 접근한 사람이 아님
        storage.setItem("jwt-auth-token", "");
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
      }
    }
  }
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