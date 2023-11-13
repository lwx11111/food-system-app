import request from '@/utils/request'

export default {
	// 分页查询
	selpage4menu(data) {
		return request({
		  url:'/menu/selpage',
		  method: 'post',
		  data: data
		})
	},
	
	// 查询单条
	sel4menu(id) {
		return request({
		  url: '/menu/' + id,
		  method: 'get',
		})
	},
	
	// 添加
	add4menu(obj) {
		return request({
		  url: '/menu',
		  method: 'post',
		  data: obj
		})
	},
}

