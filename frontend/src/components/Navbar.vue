<template>
  <nav class="navbar navbar-expand fixed-top navbar-light bg-light">
    <div class="container">
    <div class="navbar-brand">
        <a href="/" class="nav-link">Albo7</a>
      </div>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <a href="/wiki" class="nav-link">
            Wiki
          </a>
        </li>
      </div>
      <div v-if="!this.isLogin" class="navbar-nav ml-auto" >
        <li class="nav-item">
          <a href="/register" class="nav-link">
            회원가입
          </a>
        </li>
        <li class="nav-item">
          <a href="/login" class="nav-link">
            로그인
          </a>
        </li>
      </div> 

      <!-- 로그인 했을때 -->
      <div v-if="this.isLogin" class="navbar-nav ml-auto" >
        <li class="nav-item">
          <a href="/profile" class="nav-link">
            <font-awesome-icon icon="user" /> 내 정보
          </a>
        </li>
        <li class="nav-item">
          <a href class="nav-link" @click="logOut">
            <font-awesome-icon icon="sign-out-alt" /> 로그아웃
          </a>
        </li>
      </div>
      </div>
  </nav>
  <!-- <button v-if="isAuthenticated" @click.prevent="onClickLogout">Logout</button> -->
</template>

<script>
// import store from '../store'
// import axios from 'axios'

export default {
  name: 'Navbar',
  data() {
    return {
      isLogin: null,
    }
  },
  computed: {
    // 현재 로그인 한 상태인지 아닌지 체크
  },
  methods: {
    onClickLogout() {
      // LOGOUT 변이 실행 후 리다이렉트
      this.$store.dispatch('logout')
        .then(() => this.$router.push('/'))
    },
    loginChecker() {
      this.isLogin = this.$store.getters.isAuthenticated
    }
  },
  created() {
    this.loginChecker()
  },
}
</script>

<style scoped>
  @import url(https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css);
  .nav-link {
    font-size : 0.8 rem;
    font-family : 'NanumSquare', sans-serif;
  }
</style>