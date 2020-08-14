<template>
	<div class="comment-box">
		<p>작성자: {{ comment.author }} 내용:{{ comment.content }}</p>
		<v-btn 
			v-if="comment.isauthor"
			@click="onCommentDelete(comment)"
		>
		삭제</v-btn>
		<v-btn 
			v-if="comment.isauthor"
			@click="onCommentUpdate(comment)"
		>
		수정</v-btn>
	</div>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
	name: 'Comment',
	components: {
	},
	data() {
		return {
			comment: this.$attrs.comment
		}
	},
	methods: {
		isCommentauthor(Comments) {
			Comments.forEach(one_comment => {
				if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
					one_comment.isauthor = true
				} else {
					one_comment.isauthor = false
				}
			})
			return Comments
		},
		// 댓글 Delete 메서드
		onCommentDelete(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				// this.Comments.splice(this.Comments.indexOf(one_comment),1)
				axios.delete(API_URL + `api/v3/${one_comment.cid}`)
					.then(() => this.$emit('Click-Delete-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// 댓글 수정(아직 수정 필요함)
		onCommentUpdate(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				// this.Comments.splice(this.Comments.indexOf(one_comment),1)
				axios.put(API_URL + `api/v3/${one_comment.cid}`)
					.then(() => this.$emit('Click-Update-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// onClickDeleteBtn() {
		// 	this.$emit('Click-Delete-Btn')
		// },
		// onClickUpdateBtn() {
		// 	this.$emit('Click-Update-Btn')
		// },
	}
}
</script>

<style>
.comment-box {
  display: flex
}
</style>