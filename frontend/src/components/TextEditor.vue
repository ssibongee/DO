<template>
	<v-container>
		<editor ref="toastuiEditor" height="500px"/>
		<b-button @click="createAction">저장하기</b-button>
	</v-container>
</template>
<script>
import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import { Editor } from '@toast-ui/vue-editor'
import axios from 'axios'

const API_URL = 'http://localhost:8399/'
// const FLICKR_URL = 'https://up.flickr.com/services/upload/'

export default {
    name: 'TextEditor',
    components: {
        Editor,
    },
    data(){
        return {
            editorText: ''
        }
    },
    methods: {
        createAction() {
            var textdata = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
            this.editorText = textdata
            // this.$router.push({ name: 'postdetail', params: { username:'김현성', title:'이건 제목', content: this.editorText }})
            // console.log(process.env.VUE_APP_FLICKR_API_KEY)
            axios.post(API_URL+'api/v2/1', {
                author: '유저 이름',
                title: '글 제목',
                content: this.editorText,
            })
            .then(res => console.log(res))
            .catch(err => console.log(err))
        }
    },
    // hooks: {
    //     addImageBlobHook: function (blob, callback) {
    //         callback('11', 'alt text');
    //         return false;
    //     }
    // }
}
</script>