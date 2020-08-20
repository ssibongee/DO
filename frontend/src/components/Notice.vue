<template>
    <div>
    <Navbar></Navbar>
    <div class="doContent">
      <div class="feature_feed">
        <div class="inner_feature">
          <h2 id="do_top do_feed">Feed</h2>
          <p class="top_desc">내가 좋아요한 글을 한 눈에</p>
          <div class="info_feed">
            좋아요
            <span class="feed_cnt">{{posts.length}}</span>
          </div>
        </div>
      </div>
      <div class="content">
        <v-row v-if=posts.length>
          <v-col cols="4" v-for="(post, index) in posts" :key="index+'_posts'">
            <v-hover>
              <template v-slot="{hover}">
                <v-card 
                  @click="postdetail(post)"
                  height="406px"
                  max-width="350px"
                  :elevation="hover ? 8 : 2"
                >
                  <v-img
                    class="white--text align-end"
                    height="180px"
                    :src="post.thumbnail"
                  />
                  <div class="inner_card">
                    <!-- 제목이 가로범위 초과시 ... -->
                    <div class="card_title"><h5>{{post.title}}</h5></div>
                    <!-- <v-card-subtitle class="pb-0">{{ item }}</v-card-subtitle> -->
                    <!-- 내용 -->
                    <div class="card_content">
                      <p>{{post.content}}</p>
                      <div v-if="!post.content">게시글 내용이 비어있습니다.</div>
                    </div>
                    <!-- 날짜 -->
                    <div class="card_date">{{post.publishedTime}}</div>
                    <!-- 작성자, 좋아요 -->
                    <div class="card_info">
                      <div class="author">
                        {{post.author}}
                      </div>
                      <div class="liked">
                        <v-icon x-small style="margin-top:-2px" color="black">fas fa-heart</v-icon>
                        {{post.likes}}
                      </div>
                    </div>
                  </div>
                </v-card>
              </template>
            </v-hover>
          </v-col>
        </v-row>
        <v-row v-else>
          게시글이 없습니다.
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>