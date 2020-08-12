<template>
  <div>
		<header>
			<Navbar></Navbar>
			<div class="search">
				<div class="inner_box">
					<h2>검색</h2>
					<p class="do_txt">다른 Do!블로그가 궁금하다면?</p>
						<v-select
							v-model="select"
							:items="options"
							item-text="name"
							item-value="abbr"
							menu-props="auto"
							hide-details
							label="Solo field"
							solo
							flat
							style="display:inline-block; width:20%; margin: 0 5px 0 0;"
							return-object
						></v-select>
						<v-text-field
							v-model="kword"
							hide-details
							flat
							solo
							autofocus
							style="display:inline-block; width:79%"
							@keyup.enter="postList()"
						>
							<template v-slot:append>
								<v-btn depressed text color="#5c7bf4" 
									:disabled="kword.length<1 ? true:false" 
									@click="postList()"
									><v-icon>fas fa-search</v-icon></v-btn>
							</template>
						</v-text-field>
				</div>
			</div>
		</header>
		<div class="content_list">
		검색 페이지 입니다.
			<div v-for="post in posts" :key="post.pid">
				<div class="post_list">
					<div class="author_info">
						{{post.author}}
					</div>
				</div>
				{{post}}
			</div>
		</div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
// import Post from '../models/post.js'

import axios from 'axios'

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'

export default {
	name: 'Introduce',
	components: {
		Navbar,
	},
	data() {
		return{
			select: { name: '키워드', abbr: 't'},
			options: [{name: '태그', abbr: 'a'}
							, {name: '작성자', abbr: 'u'}
							, {name: '키워드', abbr: 't'},],
			kword:'',
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
	// created(){
	// 	axios.get(API_URL+'/api/v2/find/a/{tag}')
	// 		.then(({data})=>{
	// 			console.log(data);
	// 		})
	// },
	methods: {
		postList(){
			console.log(this.select.abbr)
			console.log(this.kword)
			axios
				.get(API_URL+`api/v2/find/${this.select.abbr}/${this.kword}`)
				.then(({data})=>{
					this.posts = data;
				})
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
.post_list {
	margin : 0 auto;
}
</style>