<template>
	<v-container>
		<v-text-field
			v-model="title"
			:counter="20"
			label="글 제목을 입력해주세요"
			required
		></v-text-field>
		<editor :initialValue="this.tempText" ref="toastuiEditor" height="500px"/>
		<h3>썸네일 이미지</h3>
		<div class="profile_area">
			<div class="imgbox">
				<!-- 회원 프로필 이미지 디비에서 조회 -->
				<!-- [추가]이미지 변화에 대한 처리 -->
				<img v-bind:src="thumbImage" onerror="this.src='https://www.mangboard.com/wp-content/uploads/mangboard/2017/04/07/F816_imageupload.png'">
			</div>
			<div class="btn_group">
				<!-- 이미지 업로드 (모달) -->
				<div class="img_btn">
					<v-btn depressed width="150px" color="#6e8af8" class="white--text"><label for="file-input" style="padding-top:9px">이미지 업로드</label></v-btn>
					<input type="file" accept="image/*" @change="uploadthumbImage($event)" id="file-input" class="uploadimg">
				</div>
			</div>
		</div>
		<h3>태그 입력칸</h3>        
		<TagInputBox 
			@submit-post="createAction"
			@save-post="saveAction"
		/>
	</v-container>
</template>
<script>
import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import { Editor } from '@toast-ui/vue-editor'
import axios from 'axios'
import TagInputBox from './TagInputBox.vue'

// const API_URL = 'http://localhost:8081/'
const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const storage = window.sessionStorage
// const FLICKR_URL = 'https://up.flickr.com/services/upload/'

export default {
	name: 'TextEditor',
	components: {
		Editor,
		TagInputBox
	},
	data(){
		return {
			editorText: '',
			title: '',
			thumbImage: '',
			uid: '',
			tempText: ''
		}
	},
	created(){
		this.title = storage.getItem("title")
		this.tempText = storage.getItem("content")
	},
	mounted() {
		storage.removeItem("thumbImage")
	},
	methods: {

		createAction(tagData) {
			// 태그 데이터 정리해서 보내기
			let tagList = new Array()
			tagData.forEach(tag => {
				tagList.push(tag["text"])
			})
			// console.log(tagList)
			var textdata = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
			this.editorText = textdata
			axios.post(API_URL+'api/v2/', {
				author: storage.getItem("login_user"),
				title: this.title,
				content: this.editorText,
				tag: tagList,
				thumbnail: storage.getItem("thumbImage")
			})
			.then(() => {
				storage.removeItem("thumbImage")
				// 임시 저장 storage 삭제
				storage.removeItem("title")
				storage.removeItem("content")
				this.$router.push('/')
			})
			.catch(err => console.log(err))
		},
		saveAction() {

			var textdata = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
			this.editorText = textdata
			axios.put(API_URL+"api/v2/temp",{
				pid : `${storage.getItem("pid")}`,
				uid : `${storage.getItem("uid")}`,
				author : `${storage.getItem("login_user")}`,
				title: this.title,
				content: this.editorText,
			})
			.then(() => {
				storage.removeItem("thumbImage")
				// 임시 저장 storage 삭제
				storage.removeItem("title")
				storage.removeItem("content")
				this.$router.push('/')
			})
			.catch(err => console.log(err))
		},

		// 업로드 이미지
		uploadthumbImage(e){

			var file = new FormData();
			const image = e.target.files[0];
			file.append("file", image);

			// 미리보기용
			const reader = new FileReader();
			reader.readAsDataURL(image); 
			reader.onload = e =>{
				this.thumbImage = e.target.result; // profileImage 설정
			};

			// **추가** POST문으로 서버에 이미지 파일 전송
			axios.post(API_URL+'api/v2/img'+`?title=${this.title}&author=${storage.getItem("login_user")}`,
				file,
				{
					headers:{
						'Content-Type': 'multipart/form-data',
						'Accept': 'application/json'
					}	
				}
				)
				.then(response => {
					console.log("썸네일 업로드 데이터 URL: "+response.data);
					this.thumbImage = response.data;
					storage.setItem("thumbImage", response.data)
					console.log("썸네일 스토리지 저장 완료")
				})
				.catch((err) => {
					console.log("프로필 이미지 업로드 실패")
					console.log(err);
				})
		},
	},
}
</script>

<style scoped>
h3 {
	margin-top: 20px;
	margin-bottom: 5px;
}

.uploadimg {
		display:none;
	}
.profile_area{
		padding: 30px 20px 18px;
		border-bottom: 1px solid  #e0e5ee;
	}
.img_btn{
	font-family: 'NanumSquare', Noto Sans Light, sans-serif;
	width:150px;
	margin: 5px 0;
}
.imgbox {
		width: 400px;
		height: 250px;
		border-radius: 10%;
		overflow: hidden;
		margin: 0 auto;
	}
.imgbox> img {
	width: 100%;
	height: 100%;
}
.btn_group{
	margin: 15px 0 0;
}
</style>