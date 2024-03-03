<template>
  <view class="pwd-retrieve-container">
    <uni-forms ref="form" :value="user" labelWidth="80px">
      <uni-forms-item name="oldPassword" label="旧密码">
        <uni-easyinput type="password" v-model="user.oldPassword" placeholder="请输入旧密码" />
      </uni-forms-item>
      <uni-forms-item name="newPassword" label="新密码">
        <uni-easyinput type="password" v-model="user.newPassword" placeholder="请输入新密码" />
      </uni-forms-item>
      <uni-forms-item name="confirmPassword" label="确认密码">
        <uni-easyinput type="password" v-model="user.confirmPassword" placeholder="请确认新密码" />
      </uni-forms-item>
      <button type="primary" @click="submit">提交</button>
    </uni-forms>
  </view>
</template>

<script>
	import {getEncryptPassword} from '@/utils/login/passwordEncrypt.js';
	import  Api from '@/api/auth.js'
  export default {
    data() {
      return {
        user: {
          oldPassword: undefined,
          newPassword: undefined,
          confirmPassword: undefined
        },
        rules: {
          oldPassword: {
            rules: [{
              required: true,
              errorMessage: '旧密码不能为空'
            }]
          },
          newPassword: {
            rules: [{
                required: true,
                errorMessage: '新密码不能为空',
              },
              {
                minLength: 6,
                maxLength: 20,
                errorMessage: '长度在 6 到 20 个字符'
              }
            ]
          },
          confirmPassword: {
            rules: [{
                required: true,
                errorMessage: '确认密码不能为空'
              }, {
                validateFunction: (rule, value, data) => data.newPassword === value,
                errorMessage: '两次输入的密码不一致'
              }
            ]
          }
        }
      }
    },
    onReady() {
      this.$refs.form.setRules(this.rules)
    },
    methods: {
      submit() {
        this.$refs.form.validate().then(res => {
			
			const data = {
				oldPass: this.user.oldPass,
				newPass: this.user.newPass,
				confirmPass: this.user.confirmPassword,
				accountId: localStorage.getItem("userId"),
			}
			
			data.newPass = getEncryptPassword(data.newPass, 'aes');
			data.oldPass = getEncryptPassword(data.oldPass, 'aes');
			Api.modifyPass(data).then(res => {
				console.log(res)
				if (res.code === '20000'){
					uni.showToast({
						title: `修改成功`,
						icon: 'none'
					})
					localStorage.removeItem("userId");
					this.$tab.reLaunch('/pages/login')
				} else {
					ElMessage.error(res.message)
				}
			})

        })
      }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }

  .pwd-retrieve-container {
    padding-top: 36rpx;
    padding: 15px;
  }
</style>
