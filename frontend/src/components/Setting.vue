<template>
  <v-container>
      <v-row>
          <v-col cols="1.5"></v-col>
          <v-col cols="9">
            <!-- 썸네일, 회원정보영역 -->
            <v-row>
                <v-col cols="2" class="thumbnail-area">
                    <div style="margin: 0 auto">
                        <img src="https://media.vlpt.us/images/cansoup/profile/8680ef3b-fd33-451b-a634-03e8ba82b23d/social.png?w=400" alt="profile">
                        <div class="my-2">
                            <v-btn depressed color="primary">이미지 변경</v-btn>
                        </div>
                        <div class="my-2">
                            <v-btn depressed color="primary">이미지 제거</v-btn>
                        </div>
                    </div>
                </v-col>
                <v-spacer></v-spacer>
                <v-col cols="9" class="info-area">
                    <h2>{{userinfo.nickname}}</h2>
                    <p>{{userinfo.introduce}}</p>
                    <!-- 클릭 시 name, description 부분 수정 -->
                    <div class="my-2">
                        <v-btn depressed small class="btn-success" @click="Modprofile">Modify</v-btn>
                    </div>
                </v-col>
            </v-row>
                <!-- 설정 영역 -->
            <v-list>
                <v-row dense>
                    <v-col cols="3">
                        <h3>Blog Title</h3>
                    </v-col>
                    <v-col cols="9" align-self>
                        <div>
                            {{accountinfo.blogtitle}}
                        </div>
                    </v-col>
                    <v-col cols="12" class="msg">
                        개인 페이지의 좌측 상단에 나타나는 페이지 제목입니다.
                    </v-col>
                </v-row>
                <hr>
                <v-row dense>
                    <v-col cols="3">
                        <h3>SNS info</h3>
                    </v-col>
                    <v-col cols="9">
                        <a href="/setting">수정</a>
                    </v-col>
                    <v-col cols="12" class="msg">
                        포스트 및 블로그에서 보여지는 프로필에 공개되는 소셜 정보입니다.
                    </v-col>
                </v-row>
                <hr>
                <v-row dense>
                    <v-col cols="3">
                        <h3>Email Address</h3>
                    </v-col>
                    <v-col cols="9">
                        Albo7@ssafy.com
                    </v-col>
                    <v-col cols="12" class="msg">
                        회원 인증 또는 시스템에서 발송하는 이메일을 수신하는 주소입니다.
                    </v-col>
                </v-row>
                <hr>
                <v-row dense>
                    <v-col cols="3">
                        <h3>Subscribe</h3>
                    </v-col>
                    <v-col cols="9">
                        <v-col cols="12">
                            <v-switch value inset v-model="commentAlarm" style="margin:0px" label="댓글 알림"></v-switch>
                            <v-switch value inset v-model="updateAlarm" style="margin:0px" label="업데이트 소식"></v-switch>
                        </v-col> 
                    </v-col>
                </v-row>
                <hr>
                <v-row dense>
                    <v-col cols="3">
                        <h3>Sign Out</h3>
                    </v-col>
                    <v-col cols="9">
                        <v-btn class="ma-2" tile outlined color="error">
                            회원탈퇴
                        </v-btn>
                    </v-col>
                    <v-col cols="12" class="msg">
                        탈퇴 시 작성하신 포스트 및 댓글이 모두 삭제되며 복구되지 않습니다.
                    </v-col>
                </v-row>
                <hr>
            </v-list>
          </v-col>
          <v-col cols="1.5"></v-col>
      </v-row>
  </v-container>
</template>

<script>
export default {
    data() {
        return {
            mandatory : false,
            commentAlarm : false,
            updateAlarm : false,
            userinfo : {
                nickname : 'albo7',
                introduce : 'this is team albo7'
            },
            accountinfo : {
                blogtitle : 'Albo7',
                snsinfo : null,
                emailaddr : 'albo7@albo.com',
            }
        }
    },
}
</script>

<style scoped>
    v-switch {
        margin : 0px;
    }
    hr {
        margin : 10px 0px;
    }
    .msg {
        color:grey;
    }
</style>