<template>
    <v-container>
        <v-text-field
            v-model="title"
            :counter="20"
            label="글 제목을 입력해주세요"
            required
        ></v-text-field>
        <editor ref="toastuiEditor" height="500px"/>
        <h3>태그 입력칸</h3>        
        <TagInputBox @event-data="createAction"/>
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
        }
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
                author: '유저 이름',
                title: this.title,
                content: this.editorText,
                tag: tagList,
            })
            .then(() => this.$router.push('/'))
            .catch(err => console.log(err))
        },
    },
}
</script>

<style scoped>
h3 {
    margin: 0.5rem auto;
}
</style>