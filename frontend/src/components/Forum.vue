<template>
  <div>
    <Navbar></Navbar>
		<template>
			<v-card flat style="margin-top:130px">
				<v-tabs
					background-color="transparent"
					centered
					color="black"
					slider-color="#6987f7"
					slider-size="3"
				>
					<v-tab @click="postread(item)" v-for="item in items" :key="item">
						{{ item }}
					</v-tab>
				</v-tabs>
			</v-card>
		</template>
		<div class="inner">
			<v-tabs-items v-model="tab">
				<v-tab-item v-for="(item) in items" :key="item">
					<v-row v-if="tags.length">
						
					</v-row>
				</v-tab-item>
			</v-tabs-items>
		</div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
// import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'

AOS.init();

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
// const storage = window.sessionStorage

export default {
	name: 'Forum',
	components: {
    Navbar,
	},
	data() {
		return {
			tab: null,
      items: [
        '인기순', '이름순',
      ],
		}
	}
}
</script>

<style>
.inner {
  min-width: 1100px;
  max-width: 1150px;
	padding: 130px 20px 0px 20px;
	margin: 0 auto;
}
/* .v-tab {
	margin: 0 auto;
} */
</style>