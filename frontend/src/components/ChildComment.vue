<template>
  <div>
    <div>
      <h5>{{ child.author }}</h5>
      <p>{{ child.content }}</p>
    </div>
    <div>
      <v-btn
        class="mx-2 red accent-3 text-white"
        v-if="child.isauthor"
        @click="onCommentDelete(child)"
      >삭제</v-btn>
      <v-btn 
        class="green accent-4"
        color="white"
        v-if="child.isauthor"
        @click="ClickChildUpdateBtn(child)"
      >수정</v-btn>
      <div v-if="ChildUpdateFlag" class="update-box">
				<v-text-field
					v-model="ChildUpdateInput"
					solo
					rows="2"
					clearable
				></v-text-field>
				<v-btn @click="onChildUpdate(child)" class="update-btn">수정하기</v-btn>
			</div>
    </div>
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
      child: this.$attrs.child,
      ChildCommentInput: '',
			ChildUpdateInput: '',
			ChildUpdateFlag: false,
    }
  },
  mounted() {
  },
  methods: {
    // *** Child Comment ***
		// 대댓글 수정 버튼 - ChildUpdateFlag 토글
		ClickChildUpdateBtn(one_child) {
      this.ChildUpdateInput = one_child.content
			this.ChildUpdateFlag = !this.ChildUpdateFlag
		},
		onChildUpdate(one_child) {
			if (one_child.author === storage.getItem("login_user") && one_child.uid === Number(storage.getItem("uid"))) {
				const tmp_comment = {
					content: this.ChildUpdateInput,
					cid: one_child.cid
				}
				axios.put(API_URL + `api/v3/`, tmp_comment)
					.then(() => {
						alert('수정되었습니다.')
						this.$emit('Click-Child-Update-Btn')
						this.ChildUpdateInput = ''
						this.ChildUpdateFlag = !this.ChildUpdateFlag
					})
			} else {
				alert('비정상적인 접근입니다')
			}
    },
    onCommentDelete(one_child) {
			if (one_child.author === storage.getItem("login_user") && one_child.uid === Number(storage.getItem("uid"))) {
				axios.delete(API_URL + `api/v3/${one_child.cid}`)
					.then(() => this.$emit('Click-Child-Delete-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
  }
}
</script>

<style>

</style>