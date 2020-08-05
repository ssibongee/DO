<template>
  <v-container>
    <!-- 네비게이션 바 -->
    <v-col cols="12">
      <Navbar></Navbar>
    </v-col>
    <!-- 게시글 탭 -->
    <v-tabs v-model="tab" grow class="nav">
      <v-tab v-for="item in items" :key="item">
        {{ item }}
      </v-tab>
    </v-tabs>
    <v-row>
      <v-col>
        <v-tabs-items v-model="tab">
          <!-- 카드 렌더링 -->
          <v-tab-item v-for="item in items" :key="item">
            <v-row>
              <div class="col-lg-4 col-md-6" v-for="count in vforcount" :key="count">
                <v-card
                  hover
                  @click="testpostdetail"
                >
                  <v-img
                    class="white--text align-end"
                    height="200px"
                    src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                  >
                    <v-card-title>Top 10 Australian beaches</v-card-title>
                  </v-img>

                  <v-card-subtitle class="pb-0">{{ item }} {{ count }}</v-card-subtitle>

                  <v-card-text class="text--primary">
                    <div>Whitehaven Beach</div>

                    <div>Whitsunday Island, Whitsunday Islands</div>
                  </v-card-text>

                  <v-card-actions>
                    <v-btn
                      color="orange"
                      text
                    >
                      Share
                    </v-btn>

                    <v-btn
                      color="orange"
                      text
                    >
                      Explore
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </div>
            </v-row>
          </v-tab-item>
        </v-tabs-items>
      </v-col>
      <v-col id="side" class="hidden-sm-and-down" cols="3">
        <Sidebar></Sidebar>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import Sidebar from '../components/Sidebar.vue'
import axios from 'axios'

const API_URL = 'http://localhost:8081/'

export default {
  name: 'Home',
  data() {
    return {
      vforcount: [1, 2, 3, 4, 5, 6],
      tab: null,
      items: [
        'Trending', 'Recent'
      ]
    }
  },
  components: {
    Navbar,
    Sidebar
  },
  mounted() {
  },
  methods: {
    testpostdetail() {
      axios.get(API_URL+'api/v2/p/1')
        .then(res => {
          console.log(res)
          this.$router.push({name: 'postdetail', params: { username:res.data.author, title:res.data.title, content: res.data.content}})
        })
        .catch(err => console.log(err))
        
    },
  }
}
</script>

<style>
#side {
  margin-left:2vw;
}
/* .colortest {
  background-color : 
} */
</style>
