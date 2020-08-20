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
				@click="onClickUpdateBtn(comment)"
			>수정</v-btn>
		</div>
		<div v-if="UpdateFlag" class="update-box">
			<v-text-field
				v-model="CommentUpdateInput"
				solo
				dense
				clearable
			></v-text-field>
			<v-btn @click="onCommentUpdate(comment)" class="update-btn">수정하기</v-btn>
		</div>
		<div class="comment-box">
			<v-btn @click="onClickChildBtn" class="mx-2" fab x-small dark color="indigo">
				<v-icon dark>mdi-plus</v-icon>
			</v-btn>
			<div class="my-auto">
				<p class="" v-if="!isCommentChild(comment.child)">대댓글이 없습니다 ㅠㅠ</p>
				<p class="" v-else>{{ comment.child.length }}개의 대댓글</p>
				<!-- 대댓글 부분 -->
				<div v-if="ChildFlag">
					<div v-for="child in comment.child" :key=child.cid class="comment-box">
						<p>작성자: {{ child.author }} | 내용: {{ child.content }}</p>
						<v-btn 
							v-if="child.isauthor"
							@click="onCommentDelete(child)"
						>삭제</v-btn>
						<v-btn 
							v-if="child.isauthor"
							@click="onClickUpdateBtn(child)"
						>수정</v-btn>
						<!-- <div v-for="child in comment.child" :key=child.cid>
							<ChildComment 
								:child="child"
							/>
						</div> -->
					</div>
					<div>
					<v-text-field
						v-model="ChildCommentInput"
						solo
						dense
						clearable
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
// import ChildComment from './ChildComment.vue'

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
	name: 'Comment',
	components: {
		// ChildComment,
	},
	data() {
		return {
			comment: this.$attrs.comment,
			ChildFlag: false,
			ChildCommentInput: '',
			CommentUpdateInput: '',
			UpdateFlag: false,
		}
	},
	created() {
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
				// console.log(one_comment)
				const tmp_comment = {
					content: this.CommentUpdateInput,
					cid: one_comment.cid
				}
				axios.put(API_URL + `api/v3/`, tmp_comment)
					.then(() => {
						alert('수정되었습니다.')
						this.$emit('Click-Update-Btn')
						this.CommentUpdateInput = ''
						this.UpdateFlag = !this.UpdateFlag
					})
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// Comment에 Child가 있는지 없는지 체크
		isCommentChild(one_comment_child) {
			if (one_comment_child.length) {
				this.isCommentauthor(one_comment_child)
				return true
			}
			return false
		},
		onClickUpdateBtn(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				this.CommentUpdateInput = one_comment.content
				this.UpdateFlag = !this.UpdateFlag
			}
		},
		

		// *** Child Comment ***
		onChildCommentCreate(pcid) {
			if (storage.getItem("login_user")) {
				const tmp_comment = {
					author: storage.getItem("login_user"),
					content: this.ChildCommentInput,
					uid: storage.getItem("uid"),
					pid: storage.getItem("pid"),
					parent: pcid
				}
				axios.post(API_URL + 'api/v3/r', tmp_comment)
					.then(() => this.$emit('Child-Create'))
					.catch(err => console.log(err))
			}
		},
		// 대댓글 보기, 숨기기 Boolean 반전 메서드
		onClickChildBtn() {
			this.ChildFlag = !this.ChildFlag
		},
	}
}
</script>

<style>
.comment-box {
	display: flex;
}
.update-box {
	display: flex;
}
.update-btn {
	margin-left: 5px;
}
</style>