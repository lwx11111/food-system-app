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
				<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="form.confirmPassword" type="password" class="input" placeholder="请输入重复密码" maxlength="20" />
			</view>
				<view class="input-item flex align-center" style="width: 60%;margin: 0px;">
				<view class="iconfont icon-code icon"></view>
				<input v-model="form.verifyCode" class="input" placeholder="请输入验证码" />
			<view class="login-code"> 
				<image :src="captchaUrl" @click="setCaptchUrl()" class="login-code-img"></image>
			</view>
		</view>
		<view class="action-btn">
			<button @click="handleRegister()" class="register-btn cu-btn block bg-blue lg round">注册</button>
		</view>
		</view>
		<view class="xieyi text-center">
		  <text @click="handleUserLogin()" class="text-blue">使用已有账号登录</text>
		</view>
	</view>
</template>

<script>
	import commonUtil from '@/utils/login/common-util.js'
	import { getEncryptPassword } from '@/utils/login/passwordEncrypt.js'
	import { setToken } from '@/utils/auth/auth.js'
	import Api from '@/api/auth.js'

  export default {
    data() {
      return {
        // 验证码地址
        captchaUrl: '',
        uuid: '',
        globalConfig: getApp().globalData.config,
        form: {
          username: "",
          password: "",
          confirmPassword: "",
          verifyCode: "",
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
		
      // 用户登录
      handleUserLogin() {
        this.$tab.navigateTo(`/pages/login`)
      },
    
      // 注册方法
      async handleRegister() {
        if (this.form.username === "") {
          this.$modal.msgError("请输入您的账号")
        } else if (this.form.password === "") {
          this.$modal.msgError("请输入您的密码")
        } else if (this.form.confirmPassword === "") {
          this.$modal.msgError("请再次输入您的密码")
        } else if (this.form.password !== this.form.confirmPassword) {
          this.$modal.msgError("两次输入的密码不一致")
        } else if (this.form.verifyCode === "") {
          this.$modal.msgError("请输入验证码")
        } else {
          this.$modal.loading("注册中，请耐心等待...")
          this.register()
        }
      },
      // 用户注册
      async register() {
		  var _this = this;
        const data1 = {
			verify: _this.form.verifyCode,
			uuid: _this.uuid,
			username: _this.form.username,
			password: getEncryptPassword(_this.form.password, 'md5'),
			appId: this.globalConfig.appInfo.appId,
			type:'1'
		}
		Api.register(data1).then(res => {
			console.log(res);
			if (res.code === '20000'){
				uni.showToast({
					title: `注册成功`,
					icon: 'success'
				})
				// 页面跳转
				this.$tab.navigateTo(`/pages/login`)
			} else {
				uni.showToast({
					title: `注册失败`,
					icon: 'error'
				})
			}
		})
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

      .register-btn {
        margin-top: 40px;
        height: 45px;
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
