<template>
  <div>
    <Navbar/>
    <v-container fluid fill-height>
      <div class="login_do">
        <span><h2>Sign up</h2></span>
        <p>DO!를 시작해보세요.</p>
        <v-form name="form" @submit.prevent="handleRegister" style="width:318px">
          <strong class="tit_step">회원 정보 입력</strong>
          <div class="cont_data" v-if="!successful">
            <div>
              <v-text-field
                outlined
                placeholder="이메일 주소를 입력해주세요"
                hide-details
                name="email"
                v-model="user.email"
                v-validate="'required|email|max:50'"
                type="email"
              >
              </v-text-field>
              <div class="alert-danger" v-if="submitted && errors.has('email')">
                {{errors.first('email')}}
              </div>
            </div>

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
          </div>
        </v-form>
      </div>
    </v-container>

    <div class="col-md-12">
      <div class="card card-container">
        <form name="form" @submit.prevent="handleRegister">
          <div v-if="!successful">
            <div class="form-group">
              <label for="email">이메일</label>
              <input
                type="email"
                class="form-control"
                name="email"
                v-model="user.email"
                v-validate="'required|email|max:50'"
              />
              <!-- <button @click="validate()">Validate</button> -->
              <div
                class="alert-danger"
                v-if="submitted && errors.has('email')"
              >{{errors.first('email')}}</div>
            </div>
            <div class="form-group">
              <label for="username">닉네임</label>
              <input
                type="text"
                class="form-control"
                name="username"
                v-model="user.username"
                v-validate="'required|min:3|max:20'"
              />
              <div
                class="alert-danger"
                v-if="submitted && errors.has('username')"
              >{{errors.first('username')}}</div>
            </div>
            
            <div class="form-group">
              <label for="password">비밀번호</label>
              <input
                type="password"
                class="form-control"
                name="password"
                v-model="user.password"
                v-validate="'required|min:6|max:20'"
                ref="password"
              />
              <div
                class="alert-danger"
                v-if="submitted && errors.has('password')"
              >{{errors.first('password')}}</div>
            </div>
            <div class="form-group">
              <label for="password">비밀번호 확인</label>
              <input
                type="password"
                class="form-control"
                name="confirmPassword"
                v-model="user.confirmPassword"
                v-validate="'confirmed:password'"
                data-vv-as="password"
              />
              <div
                class="alert-danger"
                v-if="submitted && errors.has('confirmPassword')"
              >{{errors.first('confirmPassword')}}</div>
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-block">회원가입 하기</button>
            </div>
          </div>
        </form>

        <div
          class="alert"
          :class="successful ? 'alert-success' : 'alert-danger'"
          v-if="message"
        >{{message}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from './Navbar.vue'
import User from '../models/user'

export default {
  name: 'register',
  components: {
    Navbar,
  },
  data() {
    return {
      user: new User('', '', '', ''),
      isLogin: this.$store.state.isLoggedIn,
      submitted: false,
      successful: false,
      message: ''
    }
  },
  mounted() {
    if (this.isLogin) {
      this.$router.push('/')
    }
  },
  methods: {
    handleRegister() {
      this.message = ''
      this.submitted = true
      this.$validator.validate().then(valid => {
        if (valid) {
          this.$store.dispatch('register', this.user)
            .then(
              data => {
                this.message = data.message
                this.successful = true
              },
              error => {
                this.message = error.message
                this.successful = false
              })
            .then(
              this.$router.push('/')
          )
        }
      })
    },
  computed: {
    loggedIn() {
      return this.$store.getters.isAuthenticated
    }
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/');
      }
    },
    
  }
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
</style>