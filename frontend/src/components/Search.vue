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
			<div class="postcount" v-if="postcnt>-1">
				<!-- kword에 밑줄 어떻게 긋지? -->
				<h3><span>{{kword}}</span>에 대한 검색 결과는 <strong>{{postcnt}}</strong>개입니다.</h3>
			</div>
			<div v-for="post in posts" :key="post.pid" class="post_list">
					<div class="profile">
						<!-- 작성자 프로필 이미지. 클릭 시 개인 블로그로 이동-->
						<a><img :src="post.thumbnail"></a>
						<div class="nickname">
							{{post.author}}
						</div>
					</div>
					<div class="post_info">
						<h2>{{post.title}}</h2>
						<p class="post_content">{{post.content}}</p>
						{{post.publishedTime}}
					</div>
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
			},
			postcnt:-1,
			date: '',
		}
	},
	mounted(){
		console.log(this.posts);
	},
	methods: {
		postList(){
			// console.log(this.select.abbr)
			// console.log(this.kword)
			axios
				.get(API_URL+`api/v2/find/${this.select.abbr}/${this.kword}`)
				.then(({data})=>{
					this.posts = data;
					this.postcnt = data.length;
				});
			// console.log(this.getFormatDate(this.posts.publishedTime));
		},
		// getFormatDate(date){
		// 	let year = date.getFullYear();
		// 	let month = (1+date.getMonth());
		// 	let day = date.getDate();
		// 	return year+"년 "+month+"월 "+day+"일" 
		// }
	}
}
</script>

<style scoped>
span {
	top:10px;
	height: 4px;
	display: inline-block;
	background-color: #08d3bc;
  /* padding-bottom:2px; */
}
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

/* 검색결과 */
.content_list {
	width: 65%;
	padding: 0 15px;
	margin : 0 auto;
	min-width: 712px;
}
.postcount{
	font-size: 1.125rem;
	margin-top: 1rem;
}
.post_list {
	margin : 0 auto;
	padding: 4rem 0;
	border-bottom : 1px solid #ddd;
}

.profile {
	display : flex;
	align-items: center;
	margin-bottom: 1.5rem;
}
.profile a {
	color : black;
	margin-right: 1rem;
}
.profile img  {
	display: block;
	width: 3rem;
	height: 3rem;
	border-radius: 1.5rem;
}
.post_content {
	margin : 0.5rem 0 2rem 0;
}
</style>