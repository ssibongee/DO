import Vue from 'vue'
import Vuex from 'vuex'
// import axios from 'axios'
import AuthService from '../services/auth-service'

Vue.use(Vuex)
<<<<<<< HEAD
    
export default new Vuex.Store({
  state: {
    initialState
=======

const storage = window.sessionStorage

export default new Vuex.Store({
  state: {
    isLoggedIn: !!storage.getItem("jwt-auth-token")
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
  },
  mutations: {
    loginSuccess(state, user) {
      state.status = {loggedIn: true}
      state.user = user
    },
    loginFailure(state){
        state.status = {}
        state.user = null
    },
    logout(state){
        state.status = {}
        state.user = null
    },
    registerSuccess(state){
        state.status = {}
    },
    registerFailure(state){
        state.status = {}
<<<<<<< HEAD
    }
=======
    },
    // login(state) {
    //   state.pending = true;
    // },
    // logout (state) {
    //   state.isLoggedIn = false;
    // }
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
  },
  actions: {
    login({commit}, user){
      return AuthService.login(user).then(
        user => {
          commit('loginSuccess', user)
          return Promise.resolve(user)
        },
        error => {
          commit('loginFailure')
          return Promise.reject(error.response.data)
        }
      )
    },
    logout({commit}) {
      AuthService.logout()
      commit('logout')
    },
    register({commit}, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess')
          return Promise.resolve(response.data)
        },
        error => {
          commit('registerFailure')
          return Promise.reject(error.response.data)
        }
      )
    }
  },
  modules: {
  },
<<<<<<< HEAD
=======
  getters: {
    isAuthenticated(state) {
      return state.isLoggedIn
    }
  }
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
})

// const resourceHost = 'http://localhost:8081'

<<<<<<< HEAD
const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
    ? {status: {loggedIn: true}, user}
    : {status: {}, user: null}
=======
// const user = JSON.parse(localStorage.getItem('user'))
// const initialState = user
//     ? {status: {loggedIn: true}, user}
//     : {status: {}, user: null}
>>>>>>> 659e7596a5a1d11121e41a3e6f7ca03f5b83615b
