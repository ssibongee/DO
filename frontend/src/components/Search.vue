<template>
  <div id="app">
		<header>
			<Navbar></Navbar>
			<div class="search">
				<div class="inner_box">
					<h2>검색</h2>
					<p class="do_txt">다른 Do!블로그가 궁금하다면?</p>
						<v-select
							v-model="e1"
							:items="options"
							menu-props="auto"
							hide-details
							label="Solo field"
							solo
							flat
							style="display:inline-block; width:20%; margin: 0 5px 0 0;"
						></v-select>
						<v-text-field
							hide-details
							flat
							solo
							autofocus
							append-icon="fas fa-search"
							:append-icon-cb="postList"
							color="black"
							style="display:inline-block; width:79%"
						/>
				</div>
			</div>
		</header>
		<div class="content_list">
		검색 페이지 입니다.
			<div v-for="post in posts" :key="post">
				
			</div>
		</div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
// import Post from '../models/post.js'

import axios from 'axios'

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
	name: 'Introduce',
	components: {
		Navbar,
	},
	data() {
		return{
			e1: '태그',
			options: ['태그', '키워드', '작성자'],
			posts : {
				pid: '',
				author: '',
        title: '',
        content: '',
        publishedTime: '',
        thumbnail:'',
      }
		}
	},
	created(){
		axios.get(API_URL+'/api/v2/find/a/{tag}')
			.then(({data})=>{
				console.log(data);
			})
	},
	methods: {
		postList(){
			console.log("hellooooooooooo")
		}
	}
}
</script>

<style scoped>
.search {
	height: 370px;
	padding-top: 170px;
	color : white;
	background-color: #6987f7;
	background-image: linear-gradient(to left,#5c7bf4 10%,#08d3bc);
}
.inner_box {
	width: 712px;
	padding: 0 92px;
	margin: 0 auto;
	min-width: 530px;
}
.content_list {
	width: 1000px;
	padding: 0 15px;
	margin : 54px auto 0;
}
</style>