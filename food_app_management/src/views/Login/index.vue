<template>
    <div class="back" style="border: 1px solid white">
        <div class="loginBack">
            <div class="login-form-content" >
                <h2 style="text-align: center">登录</h2>
                <el-form :model="form"
                         ref="formRef"
                         :rules="data.rules"
                         label-width="100px">
                    <el-form-item label="用户名"
                                  prop="username"
                                  class="input-item flex align-center">
                        <el-input style="width: 300px" v-model="form.username" />
                    </el-form-item>
                    <el-form-item label="密码"
                                  prop="password"
                                  class="input-item flex align-center">
                        <el-input v-model="form.password"
                                  style="width: 300px"
                                  show-password/>
                    </el-form-item>
                    <el-form-item label="验证码"
                                  prop="verifyCode"
                                  class="input-item flex align-center">
                        <el-input v-model="form.verifyCode"
                                  style="width: 150px">
                        </el-input>
                        <img class="login-code"
                             alt="验证码"
                             id="captchaImg"
                             :src="data.captchaUrl"
                             @click="setCaptchaUrl"/>
                    </el-form-item>
                    <el-form-item class="input-item flex align-center">
                        <el-button type="success"
                                   style="width: 215px"
                                   @click="onSubmit()">
                            登录
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import {getCurrentInstance, onMounted, reactive, ref} from 'vue'
import commonUtil from '../../utils/common-util.js';
import {getEncryptPassword} from '@/utils/passwordEncrypt';
import Api from '@/api/auth.js';

import { setToken } from '@/utils/auth/auth.js'
import { useStore } from "vuex";
import { useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const store = useStore();
const router = useRouter()
const data = reactive({
    captchaUrl: '',
    uuid: '',
    rules: {
        username: [
            {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        password: [
            {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
        verifyCode: [
            {required: true, message: '验证码不能为空', trigger: 'blur'}
        ],
    }
})
const form = reactive({
    username: '',
    password: '',
    verifyCode: '',
})

// Mounted
onMounted(() => {
    setCaptchaUrl();
})

/**
 * 获取验证码
 */
const currentInstance = getCurrentInstance();
const getCaptchaUrl = () => {
    const uuid = commonUtil.createGuid()
    data.uuid = uuid;
    return Api.getVerificationCode(uuid);
}

/**
 * 重新获取验证码
 */
const setCaptchaUrl = () => {
    data.captchaUrl = getCaptchaUrl()
    form.verifyCode = ''
}

/**
 * 登录校验
 */
const formRef = ref();
const onSubmit = () => {
    formRef.value.validate(valid => {
        if (valid) {
            loginWithCode();
        }
    })
}

/**
 * 请求后端登录
 */
const loginWithCode = () => {
    const globalProperties = currentInstance?.appContext.config.globalProperties
    const params = {
        verify: form.verifyCode,
        uuid: data.uuid,
        username: getEncryptPassword(form.username, 'aes'),
        password: getEncryptPassword(form.password, 'aes'),
    };

    Api.loginWithCode(params).then(res => {
        console.log(res)
        if (res.code === 200){
            let account = res.data;
            // 本地存储
            localStorage.setItem('userId', account.accountId)
            localStorage.setItem('userName', account.loginName)

            setToken("123")
            // 跳转
            router.push({
                path: '/homepage',
            })
        } else {
            ElMessage.error({
                message: res.message,
                type: 'error'
            })
        }
    });
}
</script>

<style scoped>


@media screen and (max-width: 1500px){
    /* 当屏幕小于1500px的时候 id为bg的元素 进行改变 */
    .back{
        background-size: contain;
        margin: 0;
        padding: 0;
    }
}

.back {
    background-image: url('/src/assets/images/bg.jpg');
    background-size: cover;
    /* 背景图片不重复 */
    background-repeat: no-repeat;
    /* 最小宽度为100% */
    min-width: 100%;
    /* 最小高度为100vh    vh: 就等于 视窗的高度  1vh = 视窗的高度的1%*/
    min-height: 100vh;
    margin: 0;
    padding: 0;
    /* border: 1px red solid; */
	font-weight: bold;

}
h2{
	font-size: 35px;
}
.loginBack{
    width:1000px;
    height:500px;
    MARGIN-RIGHT: auto;
    MARGIN-LEFT: auto;

}

.login-form-content {
    text-align: center;
    margin-top: 25%;
    margin-left: 28%;
    width: 44%;
    height: 80%;
    background-color: transparent;
	border: 1px solid transparent;
    .input-item {
        margin: 20px auto;
        height: 45px;
        border-radius: 20px;
    }

    .login-code {
        height: 38px;
        float: right;
    }

}
</style>
