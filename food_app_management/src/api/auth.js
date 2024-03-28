// 权限管理API  【菜单、角色、权限】
import request from '@/utils/request'

export default {
    getVerificationCode(data) {
        return 'http://localhost:8081/account/anon/verification-code/create?uuid=' + data;

    },

    loginWithCode(data) {
        return request({
            url: '/account/login',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    register(data) {
        return request({
            url: '/account/save',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    modifyPass(data) {
        return request({
            url: '/account/modify-password',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    logout() {
        return request({
            url: 'v1/account/logout',
            method: 'post'
        })
    },

}

