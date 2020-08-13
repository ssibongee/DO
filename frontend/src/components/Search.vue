<template>
  <div id="app">
      <v-container>
      <Navbar></Navbar>
      검색 페이지 입니다.
      </v-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
export default {
    name: 'Introduce',
    components: {
        Navbar,
    },
    data() {
        return{

        }
    }
}
</script>

<style scoped>
.container {
    padding-top:120px
}
</style>