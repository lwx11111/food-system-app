import store from '@/store'
import config from '@/config'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { toast, showConfirm, tansParams } from '@/utils/common'
import qs from "qs";

let timeout = 10000
const baseUrl = config.baseUrl
const authUrl = config.authUrl;

const request = config => {
	// 是否需要设置 token
	const isToken = (config.headers || {}).isToken === false
	config.header = config.header || {}
	if (getToken() && !isToken) {
		config.header['Authorization'] = 'Bearer ' + getToken()
	}
	// get请求映射params参数
	if (config.params === 'get') {
	let url = config.url + '?' + tansParams(config.params)
	url = url.slice(0, -1)
	config.url = url
	}

	return new Promise((resolve, reject) => {
		let url = baseUrl + config.url;
		// 判断是否是权限系统路径
		if(config.url.includes("manager")){
			url = authUrl + config.url;
		}
		// form请求
		if(config.type === 'form'){
			uni.request({
				header: {
					"Content-Type": "application/x-www-form-urlencoded"
				}, // 请求头
				method: config.method || 'get',
				timeout: config.timeout ||  timeout,
				url: url,
				data: qs.stringify(config.data),
				dataType: 'json'
			}).then(response => {
				let [error, res] = response
				console.log(error)
				console.log(res)
				resolve(res.data)
			}).catch(error => {
				  console.log(error)
				let { message } = error
				if (message === 'Network Error') {
				  message = '后端接口连接异常'
				} else if (message.includes('timeout')) {
				  message = '系统接口请求超时'
				} else if (message.includes('Request failed with status code')) {
				  message = '系统接口' + message.substr(message.length - 3) + '异常'
				}
				toast(message)
				reject(error)
			})
		} else {
			uni.request({
				method: config.method || 'get',
				timeout: config.timeout ||  timeout,
				url: url,
				data: JSON.stringify(config.data),
				header: config.header,
				dataType: 'json'
			}).then(response => {
				let [error, res] = response
				if (error) {
				  toast('后端接口连接异常')
				  reject('后端接口连接异常')
				  return
				}
				const code = res.data.code || 200
				const msg = errorCode[code] || res.data.msg || errorCode['default']
				if (code === 401) {
				  // showConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录?').then(res => {
				  //   if (res.confirm) {
				  //     store.dispatch('LogOut').then(res => {
				  //       uni.reLaunch({ url: '/pages/login' })
				  //     })
				  //   }
				  // })
				  // reject('无效的会话，或者会话已过期，请重新登录。')
				} else if (code === 500) {
				  toast(msg)
				  reject('500')
				} else if (code !== 200) {
				  toast(msg)
				  reject(code)
				}
				resolve(res.data)
			}).catch(error => {
				  console.log(error)
				let { message } = error
				if (message === 'Network Error') {
				  message = '后端接口连接异常'
				} else if (message.includes('timeout')) {
				  message = '系统接口请求超时'
				} else if (message.includes('Request failed with status code')) {
				  message = '系统接口' + message.substr(message.length - 3) + '异常'
				}
				toast(message)
				reject(error)
			})
		}
		
	})
}

export default request
