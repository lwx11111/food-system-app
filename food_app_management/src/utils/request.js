/**
 * ajax请求配置
 */
import axios from 'axios'
import {ElMessage} from 'element-plus'
import qs from "qs";
import {getToken, removeToken} from '@/utils/auth/auth'

// axios默认配置
axios.defaults.timeout = 10000 // 超时时间

// 网关地址
axios.defaults.baseURL = 'http://localhost:8081';

// 整理数据
axios.defaults.transformRequest = function(data) {
    return data
}

// 路由请求拦截
axios.interceptors.request.use(
    config => {
        // 权限系统适配网关
        // if (config.url.indexOf('/manager') !== -1) {
        //     config.baseURL = 'http://43.138.149.121:8921';
        // }

        if (getToken()) {
            config.headers['Authorization'] = getToken()
        }

        if (config.type === 'form'){
            // 后端@RequestParams注解接收
            config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
            config.data = qs.stringify(config.data)
        } else if (config.type === 'file') {
            // 后端@RequestParams注解接收
            config.headers['Content-Type'] = 'multipart/form-data'
            config.data = qs.stringify(config.data)
        } else {
            // 后端@RequestBody注解接收
            config.headers['Content-Type'] = 'application/json;charset=UTF-8'
            config.data = JSON.stringify(config.data)
        }
        return config
    },
    error => {
        return Promise.reject(error.response)
    }
)

// 路由响应拦截
axios.interceptors.response.use(
    response => {
        if (response.headers && (response.headers['content-type'] === 'application/x-msdownload' ||
                response.headers['content-type'].indexOf('application/vnd.ms-excel') !== -1 ||
                response.headers['content-type'].indexOf('application/octet-stream') !== -1)) {
            return response;
        } else {
            return response.data
        }
    },
    error => {
        console.log('err' + error) // for debug
        if (error && error.response) {
            const {status} = error.response;
            if (status === 401) {
                ElMessage.error('Token值无效，请重新登录');
                removeToken();
                router.replace('/login');
            } else {

            }
        } else {
            ElMessage.error('网络出现问题，请稍后再试');
        }
        return Promise.reject(error)
    }
)
export default axios
