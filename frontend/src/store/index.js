import Vue from 'vue'
import Vuex from 'vuex'
// import axios from 'axios'
import AuthService from '../services/auth-service'

Vue.use(Vuex)
    
export default new Vuex.Store({
  state: {
    initialState
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
    }
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
})

// const resourceHost = 'http://localhost:8081'

const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
    ? {status: {loggedIn: true}, user}
    : {status: {}, user: null}