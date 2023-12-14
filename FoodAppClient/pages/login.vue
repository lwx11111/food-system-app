<template>
  <view class="normal-login-container">
    <view class="logo-content align-center justify-center flex">
      <image style="width: 100rpx;height: 100rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
      </image>
      <text class="title">好好吃</text>
    </view>
    <view class="login-form-content">
      <view class="input-item flex align-center">
        <view class="iconfont icon-user icon"></view>
        <input v-model="form.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="form.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
      </view>
     <view class="input-item flex align-center" style="width: 60%;margin: 0px;" v-if="captchaEnabled">
        <view class="iconfont icon-code icon"></view>
        <input v-model="form.verifyCode" type="input" class="input" placeholder="请输入验证码" maxlength="4" />
        <view class="login-code"> 
          <image :src="captchaUrl" @click="setCaptchUrl" class="login-code-img"></image>
        </view>
      </view>
      <view class="action-btn">
        <button @click="onSubmit" class="login-btn cu-btn block bg-blue lg round">登录</button>
      </view>
      <view class="reg text-center" v-if="register">
        <text class="text-grey1">没有账号？</text>
        <text @click="handleUserRegister" class="text-blue">立即注册</text>
      </view>
      <view class="xieyi text-center">
        <text class="text-grey1">登录即代表同意</text>
        <text @click="handleUserAgrement" class="text-blue">《用户协议》</text>
        <text @click="handlePrivacy" class="text-blue">《隐私协议》</text>
      </view>
    </view>
     
  </view>
</template>

<script>
	import commonUtil from '@/utils/login/common-util.js'
	import ApiUser from '@/api/api_user.js'
	import { getCodeImg } from '@/api/login'
	import {getEncryptPassword} from '@/utils/login/passwordEncrypt.js'
	import { setToken } from '@/utils/auth/auth.js'
	import Api from '@/api/auth.js'
	
	export default {
		data() {
			return {
				// 验证码地址
				captchaUrl: '',
				uuid: '',
				form:{
					username: '',
					password: '',
					verifyCode: '',
				},
				codeUrl: "",
				captchaEnabled: true,
				// 用户注册开关
				register: false,
				globalConfig: getApp().globalData.config,
		}
	},
	created() {
	  this.setCaptchUrl()
	},
	methods: {
		/**
		 * 获取验证码
		 */
		getCaptchaUrl(){
			const uuid = commonUtil.createGuid()
			this.uuid = uuid;
			return 'http://localhost:8921/auth-external-microservice-lwx/v1/public/anon/verification-code/create?uuid=' + uuid;
		},
		
		/**
		 * 重新获取验证码
		 */
		setCaptchUrl(){
			this.captchaUrl = this.getCaptchaUrl()
			this.form.verifyCode = ''
		},
		
		/**
		 * 登录
		 */
		onSubmit(){
			var params = {
				verify: this.form.verifyCode,
				uuid: this.uuid,
				username: getEncryptPassword(this.form.username, 'aes'),
				password: getEncryptPassword(this.form.password, 'aes'),
				appId: '1',
				appName: 'dw'
			}
			console.log(params);
			Api.loginWithCode(params).then(res => {
				console.log(res);
				if (res.code === "20000"){
					// store存储
					this.$store.commit('setAccount',res.data.info.account);
					this.$store.commit('setUser',res.data.info.user);
					this.$store.commit('setSuperAdmin',res.data.info.superAdmin);
					this.$store.commit('setToken',res.data.token);
		
					setToken(res.data.token.accessToken);
					// sessionStorage.setItem("Auth-Token",res.data.token.accessToken)
					
					this.$tab.reLaunch('/pages/home')
				}
			});
		},
	 //  // 用户注册
	 //  handleUserRegister() {
		// this.$tab.redirectTo(`/pages/register`)
	 //  },
	 //  // 隐私协议
	 //  handlePrivacy() {
		// let site = this.globalConfig.appInfo.agreements[0]
		// this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
	 //  },
	 //  // 用户协议
	 //  handleUserAgrement() {
		// let site = this.globalConfig.appInfo.agreements[1]
		// this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
	 //  },
	 //  // 获取图形验证码
	 //  getCode() {
		// getCodeImg().then(res => {
		//   this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
		//   if (this.captchaEnabled) {
		// 	this.codeUrl = 'data:image/gif;base64,' + res.img
		// 	this.loginForm.uuid = res.uuid
		//   }
		// })
	 //  },
	 //  // 登录方法
	 //  async handleLogin() {
		// if (this.loginForm.username === "") {
		//   this.$modal.msgError("请输入您的账号")
		// } else if (this.loginForm.password === "") {
		//   this.$modal.msgError("请输入您的密码")
		// } else if (this.loginForm.code === "" && this.captchaEnabled) {
		//   this.$modal.msgError("请输入验证码")
		// } else {
		//   this.$modal.loading("登录中，请耐心等待...")
		//   this.pwdLogin()
		// }
	 //  },
	 //  // 密码登录
	 //  async pwdLogin() {
		//   // ApiUser.login(this.loginForm).then(res => {
		// 	 //  console.log(res);
		// 	 //  if(res.code === 200){
		// 		//   this.loginSuccess()
		// 	 //  }
		//   // })
		// this.$store.dispatch('Login', this.loginForm).then(() => {
		//   this.$modal.closeLoading()
		//   this.loginSuccess()
		// }).catch(() => {
		//   if (this.captchaEnabled) {
		// 	this.getCode()
		//   }
		// })
	 //  },
	 //  // 登录成功后，处理函数
	 //  loginSuccess(result) {
		// // 设置用户信息
		// this.$store.dispatch('GetInfo').then(res => {
		//   this.$tab.reLaunch('/pages/home')
		// })
	 //  }
	}
	}
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }

  .normal-login-container {
    width: 100%;

    .logo-content {
      width: 100%;
      font-size: 21px;
      text-align: center;
      padding-top: 15%;

      image {
        border-radius: 4px;
      }

      .title {
        margin-left: 10px;
      }
    }

    .login-form-content {
      text-align: center;
      margin: 20px auto;
      margin-top: 15%;
      width: 80%;

      .input-item {
        margin: 20px auto;
        background-color: #f5f6f7;
        height: 45px;
        border-radius: 20px;

        .icon {
          font-size: 38rpx;
          margin-left: 10px;
          color: #999;
        }

        .input {
          width: 100%;
          font-size: 14px;
          line-height: 20px;
          text-align: left;
          padding-left: 15px;
        }

      }

      .login-btn {
        margin-top: 40px;
        height: 45px;
      }
      
      .reg {
        margin-top: 15px;
      }
      
      .xieyi {
        color: #333;
        margin-top: 20px;
      }
      
      .login-code {
        height: 38px;
        float: right;
      
        .login-code-img {
          height: 38px;
          position: absolute;
          margin-left: 10px;
          width: 200rpx;
        }
      }
    }
  }

</style>
