<template>
  <div>
      <Navbar></Navbar>
        소개 페이지 입니다.
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
        return {
            
        }
    }
}
</script>

<style scoped>
.container {
    padding-top:120px
}
</style>