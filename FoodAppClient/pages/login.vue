<template>
	<view class="normal-login-container">
		<!-- 标题 -->
		<view class="logo-content align-center justify-center flex">
			<image style="width: 400rpx;height: 400rpx;" src="/static/images/mg.png" mode="widthFix">
			</image>
			<!-- <text class="title">{{globalConfig.appInfo.appName}}</text> -->
		</view>
		<!-- 内容 -->
		<view class="login-form-content">
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="form.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="form.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
			</view>
			<view class="input-item flex align-center" style="width: 60%;margin: 0px;">
				<view class="iconfont icon-code icon"></view>
				<input v-model="form.verifyCode" type="input" class="input" placeholder="请输入验证码" maxlength="4" />
				<view class="login-code"> 
				  <image :src="captchaUrl" @click="setCaptchUrl()" class="login-code-img"></image>
				</view>
			</view>
			<view class="action-btn">
				<button @click="onSubmit()" class="login-btn cu-btn block bg-blue lg round">登录</button>
			</view>
			<view class="reg text-center">
				<text class="text-grey1">没有账号？</text>
				<text @click="handleUserRegister()" class="text-blue">立即注册</text>
			</view>
			<view class="reg text-center">
				<text class="text-grey1">不想登录？</text>
				<text @click="touristMode()" class="text-blue">游客浏览</text>
			</view>
		</view>
	</view>
</template>

<script>
	import commonUtil from '@/utils/login/common-util.js'
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
			return this.globalConfig.authUrl + this.globalConfig.managerPrefix + 'v1/public/anon/verification-code/create?uuid=' + uuid;
		},
		
		/**
		 * 重新获取验证码
		 */
		setCaptchUrl(){
			this.captchaUrl = this.getCaptchaUrl()
			this.form.verifyCode = ''
		},
		
		/**
		 * 游客模式
		 */
		touristMode() {
			 localStorage.setItem('isTourist', true);
			 this.$tab.reLaunch('/pages/home')
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
				appId: this.globalConfig.appInfo.appId,
				appName: this.globalConfig.appInfo.appName
			}
		
			Api.loginWithCode(params).then(res => {
				if (res.code === "20000"){
					console.log(res);
					let account = res.data.info.account
					// store存储
					this.$store.commit('setAccount',res.data.info.account);
					this.$store.commit('setUser',res.data.info.user);
					this.$store.commit('setSuperAdmin',res.data.info.superAdmin);
					this.$store.commit('setToken',res.data.token);
					// 本地存储
		            localStorage.setItem('userId', account.accountId)
		            localStorage.setItem('userName', account.loginName)
					
					setToken(res.data.token.accessToken);
					this.$tab.reLaunch('/pages/home')
				} else {
					uni.showToast({
						title: res.message,
						icon: 'error'
					})
				}
			});
		},
		// 用户注册
		handleUserRegister() {
			this.$tab.redirectTo(`/pages/register`)
		},
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
