<template>
  <div>
    <Navbar></Navbar>
      <div class="doContent">
         <div class="feature_feed">
            <div class="inner_feature">
               <h2 id="do_top do_feed">Forum</h2>
               <p class="top_desc"><span style="font-style:'Inter', sans-serif; font-weight:800">DO!</span>의 모든 태그들을 만나보세요</p>
            </div>
         </div>
         <div id="app" class="innner">
            <cloud 
               :data="words" 
               :fontSizeMapper="fontSizeMapper"
               :colors="colors"
               @submit-tagname="searchtagname"
            />
         </div>
         
      </div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import Cloud from '../components/Cloud.vue'
import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'
// import Cloud from 'vue-d3-cloud'

AOS.init();

const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
// const storage = window.sessionStorage

export default {
   name: 'Forum',
   components: {
      Navbar,
      Cloud
   },
   props: {
      onWordClick: {
         type: Function,
         default: (word) => { 
            axios.get(API_URL+'api/v4/'+word.text)
               .then(({data})=>{
                  // 콘텐츠 미리보기 슬라이스
                  data.forEach(el => {
                  el.tmp = el.content
                  // console.log(el)
                  if (el.content.length > 40) {
                     // 마크다운 사진 제외
                     el.content = el.content.replace(/!\[.*\)+/, "")
                     // 최대 길이 슬라이스
                     el.content = el.content.slice(0, 40)
                  }
                  // 작성 날짜만 보이게 수정
                  let year = el.publishedTime.slice(0,4);
                  let month = el.publishedTime.slice(5,7);
                  let day = el.publishedTime.slice(8,10);
                  el.publishedTime = year+"년 "+month+"월 "+day+"일"
                  // this.posts = data
                  });
                  console.log(data);
                  return {test:data}
               })
         },
      },
   },
   data() {
      return {
         tab: null,
         words: [],
         colors:["ffbe0b","628AF8","8581E6","A879D4","CB70C1","EE67AF","FF63A6","fb5607","ff006e","8338ec","3a86ff","11D9C3","23C7CF","f8961e","f9c74f","90be6d","43aa8b","EE67AF","FF63A6","11D9C3","23C7CF","35B6DB","47A4E6"],
         // ["11D9C3","23C7CF","f8961e","f9c74f","90be6d","43aa8b","EE67AF","FF63A6","11D9C3","23C7CF","35B6DB","47A4E6"],
         // ["11D9C3","23C7CF","35B6DB","47A4E6","5993F2"
         // ,"628AF8","8581E6","A879D4","CB70C1","EE67AF","FF63A6"],
         fontSizeMapper: word => Math.log2(word.value) * 3,
         spiral: "archimedean",
         tag: '',
         posts : {
            pid: '',
            title: '',
            content: '',
            publishedTime: '',
            thumbnail:'',
         },
      }
   },
   created(){
      var vm = this;
      axios.get(API_URL+"api/v4")
      .then(function(response) {
        // console.log(response);
        vm.words = response.data;
      })
      .catch(function(err) {
        console.log(err);
      })
   },
   computed: {
      
   },
   mounted(){
      this.onWordClick();
   },
   methods: {
      searchtagname(name){
         console.log(name);
      }
   },
}
</script>

<style>
.feature_feed{
  height: 133px;
  padding: 151px 0 110px;
  border-bottom: solid 2px black;;
}
.inner_feature h2 {
  font-family: 'Inter', sans-serif;
  font-size: 24px;
  font-weight: 800;
}
.top_desc {
  padding:0 0;
  font-size: 20px;
  line-height: 1;
  letter-spacing: -0.6px;
  font-family: Noto Sans Light, sans-serif;
}
.doContent {
  max-width: 1150px;
  min-width: 1100px; 
  margin: 0 auto 0;
  padding: 0 20px;
}
.feature_feed{
  height: 133px;
  padding: 151px 0 110px;
  border-bottom: solid 2px black;;
}
.feature_main {
  width: 100%;
  height: 133px;
  padding: 151px 0 0;
}
.innner {
   padding-top:20px;
  min-width: 1100px;
  max-width: 1150px;
   margin: 0 auto;
}
.wordCloud{
   text-align: center;
}
.wordCloud text{
  font-family: 'Inter','NanumSquareR','나눔스퀘어','Noto Sans DemiLight','Apple SD Gothic','맑은고딕','Nanum Gothic',sans-serif !important;
}
</style>