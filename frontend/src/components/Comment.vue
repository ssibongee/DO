<template>
	<div>
		<div class="comment-box justify-content-between">
			<div>
				<h5>{{ comment.author }}</h5>
				<p class="mr-3">{{ comment.content }}</p>
			</div>
			<div>
				<v-btn
					class="mx-2 red accent-3"
					color="white"
					v-if="comment.isauthor"
					@click="onCommentDelete(comment)"
				>삭제</v-btn>
				<v-btn
					class="green accent-4"
					v-if="comment.isauthor"
					@click="ClickUpdateBtn(comment)"
				>수정</v-btn>
			</div>
		</div>
		<div v-if="UpdateFlag" class="update-box">
			<v-text-field
				v-model="CommentUpdateInput"
				solo
				rows="2"
				clearable
			></v-text-field>
			<v-btn @click="onCommentUpdate(comment)" class="update-btn">수정하기</v-btn>
		</div>
		<div class="comment-box">
			<v-btn @click="ClickChildBtn" class="mx-2" fab x-small dark color="indigo">
				<v-icon v-if="!ChildFlag" dark>mdi-plus</v-icon>
				<v-icon v-if="ChildFlag">mdi-minus</v-icon>
			</v-btn>
			<div class="my-auto">
				<p class="" v-if="!isCommentChild(comment.child)">대댓글이 없습니다 ㅠㅠ</p>
				<p class="" v-else>{{ comment.child.length }}개의 대댓글</p>
			</div>
		</div>
			
				<!-- 대댓글 부분 -->
				<div v-if="ChildFlag">
					<div class="child-box">
						<v-text-field
							v-model="ChildCommentInput"
							flat
							outlined
							clearable
							rows="2"
						></v-text-field>
						<v-btn class="Child-Create-Btn" @click="onChildCommentCreate(comment.cid)">대댓글 작성하기</v-btn>
					</div>
					<div v-for="child in comment.child" :key="child.cid" class="child-comment justify-content-between">
						<div>
							<h5>{{ child.author }}</h5>
							<p>{{ child.content }}</p>
						</div>
						<div>
							<v-btn
								class="mx-2 red accent-3 text-white"
								color="white"
								v-if="child.isauthor"
								@click="onCommentDelete(child)"
							>삭제</v-btn>
							<v-btn 
								class="green accent-4"
								color="white"
								v-if="child.isauthor"
								@click="ClickChildUpdateBtn(child)"
							>수정</v-btn>
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
			CommentUpdateInput: '',
			UpdateFlag: false,
			ChildFlag: false,
			ChildCommentInput: '',
			ChildUpdateInput: '',
			ChildUpdateFlag: false,
		}
	},
	created() {
	},
	methods: {
		// *** Parent Comment ***
		// 댓글 Delete 메서드
		onCommentDelete(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				axios.delete(API_URL + `api/v3/${one_comment.cid}`)
					.then(() => this.$emit('Click-Delete-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// 댓글 수정
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
				// one_comment_child.forEach(element => {
				// 	this.isCommentauthor(element)
				// })
				return true
			}
			return false
		},
		ClickUpdateBtn(one_comment) {
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
		ClickChildBtn() {
			this.ChildFlag = !this.ChildFlag
			this.$nextTick()
		},
		// 대댓글 수정 버튼
		ClickChildUpdateBtn() {
			this.ChildUpdateFlag = !this.ChildUpdateFlag
		}

	}
}
</script>

<style scoped>
.comment-box {
	display: flex;
}
.child-box {
	display: inline;
}
.update-box {
	display: flex;
}
.update-btn {
	margin-left: 5px;
}
.Child-Create-Btn {
	margin-bottom: 2rem;
}
.child-comment {
	display: flex;
	margin: 2rem 0 2rem 0;
  padding: 0 0 0.5rem 0;
  border-bottom: 1px solid #888888
}
</style>