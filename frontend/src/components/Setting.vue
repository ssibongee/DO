<template>
  <v-container style="width:70%">
		<v-row class="mb-10">

			<v-col cols="12" md="3">
				<div class="wrapper">
					<div class="inner">
						<!-- <img src="http://i3a507.p.ssafy.io/img/ssafy/30d512f96f6c55318745d4e81cd2c78428407f26491e88af88d52787f6d8f10d3d9109e433ddcda8ac4adf2c4eec9303df0d831e9e6138e6db2c7294173dab08/1e67b068654a279608f42750ac4b6596a800724fa6422f0c3a77249469294a376ef61645eb0453ed46fa1afde336bc3df53e5e788b5e978a0f77bfdd28cc6477.png" height="200" width="200"> -->
						<!-- <img src="/home/ubuntu/vlog/ssafy/30d512f96f6c55318745d4e81cd2c78428407f26491e88af88d52787f6d8f10d3d9109e433ddcda8ac4adf2c4eec9303df0d831e9e6138e6db2c7294173dab08/777c534fd04b2cc000819eaf0a63bfa135a62b42777ea4650c2743ca297b3ac6d33c001c664485c7cb3cd3a08475cd80c434be670c01f16d61218f7f9fe0bde5.png"/> -->

						<div class="circle">
							<!-- 회원 프로필 이미지 디비에서 조회 -->
							<!-- [추가]이미지 변화에 대한 처리 -->
							<img v-bind:src="userinfo.profileImage" alt="profile">
						</div>

						<div class="my-2">
							<!-- 모달 창으로 이미지 업로드 -->
							<!-- <input type="file" accept="image/*" @change="uploadImage($event)" id="file-input"> -->
							<!-- <v-file-input width="120px" type="file" accept="image/*" @change="onChange">이미지 변경</v-file-input> -->
							<input type="file" accept="image/*" @change="uploadImage($event)" id="file-input">
							<!-- <div> -->
								<!-- <img v-bind:src="userinfo.profileImage" class="uploading-image"/> -->
								<!-- <input type="file" accept="image/jpeg" @change=uploadImage> -->
							<!-- </div> -->
						</div>

						<div>
							<!-- 기본 이미지로 대체 후, 데이터베이스에서 String = null -->
							<v-btn depressed width="120px" @click="deleteProfileImage">이미지 제거</v-btn>
						</div>
					</div>
				</div>
			</v-col>


			<v-col v-if="!clickprofile">
				<!-- 사용자 nickname과 introduce 가져오기 -->
				<h2>{{userinfo.nickname}}</h2>
				<p>{{userinfo.introduce}}</p>	
				<div class="my-2">
						<v-btn depressed small class="btn-success" @click="modprofile">수정</v-btn>
				</div>
			</v-col>


			<!-- 클릭 시 name, description 부분 수정 -->
			<v-col v-if="clickprofile">
				<v-text-field outlined v-model="userinfo.nickname" hide-details style="font-size: 2em; margin-bottom:5px;"></v-text-field>
				<v-text-field outlined v-model="userinfo.introduce" hide-details dense></v-text-field>
				<div class="my-2">
					<v-btn depressed small class="btn-success" @click="updateNickname">수정완료</v-btn>
				</div>
			</v-col>


		</v-row>
					<!-- 설정 영역 -->
		<v-flex>
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
						<!-- 접었다 펴는 형식으로 SNS 입력창 뜰 수 있게 설정 -->
						<!-- <a href="/setting">수정</a> -->
					<v-col v-if="!clicksns">
						<div class="my-2">
							<v-btn depressed small class="btn-success" @click="modSNS">수정</v-btn>
						</div>
					</v-col>


					<!-- 클릭 시 name, description 부분 수정 -->
					<v-col v-if="clicksns">
						
						<!-- 이메일(수정불가), GitHub, Instagram, FaceBook  -->
						<div>
							<v-text-field 
							prepend-icon="fab fa-github" 
							v-model="userinfo.github"
							hint="Github address"
							prefix="https://github.com/"
							></v-text-field>
						</div>
						<div>
							<v-text-field 
							prepend-icon="fab fa-instagram" 
							v-model="userinfo.instagram"
							hint="Instagram address"
							prefix="https://instagram.com/"
							></v-text-field>
						</div>
						<div>
							<v-text-field 
							prepend-icon="fab fa-facebook"
							v-model="userinfo.facebook"
							hint="Facebook address"
							prefix="https://facebook.com/"
							></v-text-field>
						</div>
						<!-- 자신 email로 고정 -->
						<!-- <div>
							<v-text-field 
							disabled
							prepend-icon="far fa-envelope"
							v-model="userinfo.emailaddr"
							></v-text-field>
						</div> -->
						<div class="my-2">
							<v-btn depressed small class="btn-success" @click="updateSNS">수정완료</v-btn>
						</div>
					</v-col>

					


				</v-col>
				<v-col cols="12" class="msg">
						포스트 및 블로그에서 보여지는 프로필에 공개되는 소셜 정보입니다.
				</v-col>
		</v-row>
		<hr>
		
					<v-row dense>
							<v-col cols="3">
									<h3>Donation QR 코드 업로드</h3>
							</v-col>
							<v-col cols="9">
								<!-- 데이터베이스에서 QR 이미지 가져오기 -->
								<img v-bind:src="userinfo.qrImage" alt="QR코드 생성방법" width="200px" height="200px">
							</v-col>
								
							<div class="my-2">
								<!-- 모달 창으로 이미지 업로드 -->
								<!-- <input type="file" accept="image/*" @change="uploadImage($event)" id="file-input"> -->
								<!-- <v-file-input width="120px" type="file" accept="image/*" @change="onChange">이미지 변경</v-file-input> -->
								<input type="file" accept="image/*" @change="uploadQR($event)" id="file-input">
								<!-- <div> -->
									<!-- <img v-bind:src="userinfo.profileImage" class="uploading-image"/> -->
									<!-- <input type="file" accept="image/jpeg" @change=uploadImage> -->
								<!-- </div> -->
							</div>
							<div>
								<!-- 기본 이미지로 대체 후, 데이터베이스에서 String = null -->
								<v-btn depressed width="120px" @click="deleteQRImage">이미지 제거</v-btn>
							</div>

							<!-- <v-btn>QR 이미지 업로드</v-btn> -->
								<v-col cols="12" class="msg">
								후원 받을 QR코드를 이미지 파일로 등록해주세요.
						</v-col>
					</v-row>
					<hr>
					<v-row dense>
							<v-col cols="3">
									<h3>Email Address</h3>
							</v-col>
								<v-col cols="9">
								<!-- 데이터베이스에서 email 값 가져오기 -->
									{{userinfo.emailaddr}}
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
								<!-- 이 기능 추가할 지 상의. -->
									<v-col cols="12">
											<v-switch value inset color="#FF5733" v-model="commentAlarm" style="margin:0px" label="댓글 알림"></v-switch>
											<v-switch value inset color="#FF5733" v-model="updateAlarm" style="margin:0px" label="업데이트 소식"></v-switch>
									</v-col> 
							</v-col>
					</v-row>
					<hr>
					<v-row dense>
							<v-col cols="3">
									<h3>Sign Out</h3>
							</v-col>
							<v-col cols="9">
								<!-- 회원탈퇴 누를 경우 delete 요청 -->
								<!-- 회원탈퇴 모달 창 -->
								<div>
									<div class="mb-1">
										<b-button @click="showMsgBoxTwo">회원 탈퇴</b-button>
									
									</div>
								</div>
									

							</v-col>
							
					</v-row>
					<hr>
		</v-flex>
  </v-container>
</template>

<script>
import axios from 'axios'


const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'

export default {
	props: {
		iconName: {
		type: String,
		default: 'box'
		},
		width: {
		type: [Number, String],
		default: 18
		},
		height: {
		type: [Number, String],
		default: 18
		},
		iconColor: {
		type: String,
		default: 'currentColor'
		}
	},
	data() {
		return {
			clickprofile : false,
			clicksns : false,
			mandatory : false,
			commentAlarm : false,
			updateAlarm : false,
			boxTwo: '', // 회원 탈퇴
			userinfo : {
				uid : '',
				profileImage : '',
				nickname : '',
				introduce : '',
				blogtitle : '',
				github : '',
				facebook : '',
				instagram : '',
				emailaddr : '',
				qrImage : ''
			},
			accountinfo : {

			},
			item:{
				image : null,
				imageUrl: null,
				previewImage:null
			},
			rules: [
				value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
			],	
		}
	},
	methods: {

		modprofile() {
			if(this.clickprofile===false){
				this.clickprofile = true;
			}
		},
		modSNS(){
			if(this.clicksns===false){
				this.clicksns = true;
			}
		},


		// nickname, introduce 수정
		updateNickname() {
			if(this.clickprofile===true){
				this.clickprofile = false;
				axios.put(API_URL+'api/v1/'+this.userinfo.uid,{
					email : this.userinfo.emailaddr,
					nickname : this.userinfo.nickname,
					introduce : this.userinfo.introduce,
					updateType : "1" 
				})
				.then(function (response){
					console.log(response);
				})
				.catch(function(error){
					console.log(error);
				})

			}
		},

		// sns 계정 수정
		updateSNS() {
			if(this.clicksns===true){
				this.clicksns = false;
				axios.put(API_URL+'api/v1/{uid}',{
					email : this.userinfo.emailaddr,
					instagram : this.userinfo.instagram,
					github : this.userinfo.github,
					facebook : this.userinfo.facebook,
					updateType : "2" 
				})
				.then(function (response){
					console.log(response);
				})
				.catch(function(error){
					console.log(error);
				})
			}
		},

		// 업로드 이미지
		uploadImage(e){
			var file = new FormData();
			const image = e.target.files[0];
			file.append("file", image);

			console.log("file : "+file);

			// 미리보기용
			const reader = new FileReader();
			reader.readAsDataURL(image); 
			reader.onload = e =>{
				this.userinfo.profileImage = e.target.result; // profileImage 설정
			};

			// **추가** POST문으로 서버에 이미지 파일 전송
			axios.post(API_URL+'api/v1/img?nickname='+this.userinfo.nickname,
				file,
				{
					headers:{
						'Content-Type': 'multipart/form-data',
						'Accept': 'application/json'
					}	
				}
			)
			.then(function (response){
				console.log("profileImage:"+ response);
				this.userinfo.profileImage = response;
			})
			.catch(function(error){
				console.log(error);
			})

		},
		// 기본 이미지로 수정
		deleteProfileImage(){
	
			
			// **추가** POST문으로 서버에 기본 이미지 파일 전송
			axios.put(API_URL+'api/v1/img/'+this.userinfo.uid,{
				url : "http://i3a507.p.ssafy.io/img/common/emptyProfile.png",
			})
			.then(function(response){
				console.log(response);
				// 문자열 타입이라서 안되는 것인가?
				this.userinfo.profileImage = response;
			})
			.catch(function(error){
				console.log(error);
			})

		},
		
		// 업로드 QR코드
		uploadQR(e){
			var file = new FormData();
			const image = e.target.files[0];
			file.append("file", image);

			// console.log("QRform : "+image);

			const reader = new FileReader();
			reader.readAsDataURL(image); 
			reader.onload = e =>{
				this.userinfo.qrImage = e.target.result; // profileImage 설정
			};
			
			// **추가** POST문으로 서버에 이미지 파일 전송
			axios.post(API_URL+'api/v1/qr/?nickname='+this.userinfo.nickname,
				file,
				{
					headers:{
						'Content-Type': 'multipart/form-data',
						'Accept': 'application/json'
					}	
				}
			)
			.then(function (response){
				console.log("qrImage :"+response);
				this.userinfo.qrImage = response;
			})
			.catch(function(error){
				console.log(error);
			})

		},
		deleteQRImage(){
			
			// **추가** POST문으로 서버에 기본 이미지 파일 전송
			axios.put(API_URL+'api/v1/qr/'+this.userinfo.uid,{
				url : "http://i3a507.p.ssafy.io/img/common/emptyQR.jpg",
			})
			.then(function(response){
				console.log(response);
				this.userinfo.qrImage = response;
			})
			.catch(function(error){
				console.log(error);
			})
		
		},
		// 회원탈퇴 토글 -> 모달창
		handle_toggle: function(){ 
			this.is_show = !this.is_show; // #2, #3
		},
		showMsgBoxTwo() {
			this.boxTwo = ''
			this.$bvModal.msgBoxConfirm('탈퇴 시 작성하신 포스트 및 댓글이 모두 삭제되며 복구되지 않습니다.', {
				title: '회원탈퇴',
				size: 'lg',
				buttonSize: 'lg',
				okVariant: 'danger',
				okTitle: '네, 탈퇴하겠습니다.',
				cancelTitle: '아니요',
				footerClass: 'p-2',
				hideHeaderClose: false,
				centered: true
			})
			.then(value => {
				this.boxTwo = value
				if(value === true){
					axios.delete(API_URL+'api/v1/'+this.userinfo.uid)
					.then(function (response){
						console.log(response);
					})
					.catch(function(error){
						console.log(error);
					});
				}
			})
			.catch(err => {
			// An error occurred
				console.log(err)
			})
		}
	},
	created(){
		// String userinfo = storage.get("")
		var uid = sessionStorage.uid;
		axios
		.get(API_URL+'api/v1/'+uid)
		.then(({data})=>{
			console.log("들어오는 Data 확인 : "+data.uid);
			console.log("들어오는 Data 확인 : "+data.nickname);
			console.log("들어오는 Data 확인 : "+data.email);
			console.log("들어오는 Data 확인 : "+data.password);
			console.log("들어오는 Data 확인 : "+data.profileImage);
			console.log("들어오는 Data 확인 : "+data.qrImage);
			console.log("들어오는 Data 확인 : "+data.admin);
			console.log("들어오는 Data 확인 : "+data.facebook);
			console.log("들어오는 Data 확인 : "+data.instagram);
			console.log("들어오는 Data 확인 : "+data.github);
			console.log("들어오는 Data 확인 : "+data.introduce);
			console.log("들어오는 Data 확인 : "+data.updateType);
			console.log("=======================================");
			this.userinfo.uid = data.uid;
			this.userinfo.profileImage = data.profileImage;
			this.userinfo.nickname = data.nickname;
			this.userinfo.introduce = data.introduce,
			this.userinfo.github = data.github,
			this.userinfo.facebook = data.facebook,
			this.userinfo.instagram = data.instagram,
			this.userinfo.emailaddr = data.email,
			this.userinfo.qrImage = data.qrImage

			console.log("Userinfo uid 데이터 확인 "+ this.userinfo.uid);
			// 프로필 이미지 경로가 한글이면 불러오지를 못한다.
			console.log("Userinfo profile 데이터 확인 "+ this.userinfo.profileImage);
			console.log("Userinfo qrImage 데이터 확인 "+ this.userinfo.qrImage);
		})
	},
	
}
</script>

<style scoped>
	.wrapper {
		text-align: center;
	}
	.inner {
		max-width:150px;
		display: inline-block;
	}
	.circle {
		width:8rem;
		height:8rem;
		border-radius: 50%;
		overflow: hidden;
	}
  img {
		display: block;
		margin: 0 auto;
		max-height: 100%
	}
</style>