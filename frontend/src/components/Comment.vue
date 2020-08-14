<template>
	<div class="">
		<div class="comment-box">
			<p class="mr-3">작성자: {{ comment.author }} 내용:{{ comment.content }}</p>
			<v-btn 
				v-if="comment.isauthor"
				@click="onCommentDelete(comment)"
			>삭제</v-btn>
			<v-btn 
				v-if="comment.isauthor"
				@click="onCommentUpdate(comment)"
			>수정</v-btn>
		</div>
		<div class="comment-box">
			<v-btn @click="onClickChildBtn" class="mx-2" fab x-small dark color="indigo">
				<v-icon dark>mdi-plus</v-icon>
			</v-btn>
			<div class="my-auto">
				<p class="" v-if="!isCommentChild(comment.child)">대댓글이 없습니다 ㅠㅠ</p>
				<p class="" v-else>{{ comment.child.length }}개의 대댓글</p>
				<div v-if="ChildFlag">
					<div v-for="child in comment.child" :key=child.cid>
						<p>작성자: {{ child.author }} | 내용: {{ child.content }}</p>
					</div>
					<div>
					<v-text-field
						:id="comment.cid"
						v-model="ChildCommentInput"
						solo="true"
						dense="true"
						clearable="true"
					></v-text-field>
					<v-btn @click="onChildCommentCreate(comment.cid)">답글 작성하기</v-btn>
					</div>
				</div>
			</div>
		</div>
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
			comment: this.$attrs.comment,
			ChildFlag: false,
			ChildCommentInput: '',
		}
	},
	methods: {
		// *** Parent Comment ***
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
				axios.delete(API_URL + `api/v3/${one_comment.cid}`)
					.then(() => this.$emit('Click-Delete-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// 댓글 수정(아직 수정 필요함)
		onCommentUpdate(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				axios.put(API_URL + `api/v3/${one_comment.cid}`)
					.then(() => this.$emit('Click-Update-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// Comment에 Child가 있는지 없는지 체크
		isCommentChild(one_comment_child) {
			console.log(one_comment_child)
			if (one_comment_child.length) return true
			return false
		},
		// 대댓글 보기, 숨기기 Boolean 반전 메서드
		onClickChildBtn() {
			this.ChildFlag = !this.ChildFlag
		},

		// *** Child Comment ***
		onChildCommentCreate(pid) {
			if (storage.getItem("login_user")) {
				const tmp_comment = {
					author: storage.getItem("login_user"),
					content: this.ChildCommentInput,
					uid: storage.getItem("uid"),
					pid: storage.getItem("pid"),
					parent: pid
				}
				axios.post(API_URL + 'api/v3/r', tmp_comment)
					.then(() => this.$emit('Child-Create'))
					.catch(err => console.log(err))
			}
		}
	}
}
</script>

<style>
.comment-box {
  display: flex
}
</style>