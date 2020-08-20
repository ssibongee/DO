<template>
  <div>
    <!-- <h2>Child Comment 자리</h2> -->
    <!-- <v-btn 
      v-if="child.isauthor"
      @click="onCommentDelete(child)"
    >삭제</v-btn>
    <v-btn 
      v-if="child.isauthor"
      @click="onClickChildUpdateBtn(child)"
    >수정</v-btn> -->
    <!-- <div v-if="ChildUpdateFlag" class="update-box">
			<v-text-field
				v-model="CommentUpdateInput"
				solo
				dense
				clearable
			></v-text-field>
			<v-btn @click="onCommentUpdate(comment)" class="update-btn">수정하기</v-btn>
		</div> -->
  </div>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const storage = window.sessionStorage

export default {
  name: 'ChildComment',
  data() {
    return {
      Child: this.$attrs.child,
      ChildUpdateFlag: false,
    }
  },
  mounted() {
  },
  methods: {
    isCommentauthor(one_comment) {
      if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
        one_comment.isauthor = true
      } else {
        one_comment.isauthor = false
      }
    },
    // onCommentDelete(one_comment) {
    //   if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
    //     axios.delete(API_URL + `api/v3/${one_comment.cid}`)
    //       .then(() => this.$emit('Click-Delete-Btn'))
    //   } else {
    //     alert('비정상적인 접근입니다')
    //   }
    // },
		onChildCommentUpdate(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				const tmp_comment = {
					content: this.CommentUpdateInput,
					cid: one_comment.cid
				}
				axios.put(API_URL + `api/v3/`, tmp_comment)
					.then(() => {
						alert('수정되었습니다.')
						this.$emit('Click-Child-Update-Btn')
						this.CommentUpdateInput = ''
						this.UpdateFlag = !this.UpdateFlag
					})
			} else {
				alert('비정상적인 접근입니다')
			}
    },
    ChildUpdateBtn(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				this.CommentUpdateInput = one_comment.content
				this.ChildUpdateFlag = !this.ChildUpdateFlag
			}
		},
  }
}
</script>

<style>

</style>