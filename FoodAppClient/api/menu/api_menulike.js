import request from '@/utils/request'



export default {
	// 参数删除
	deleteMenuLikeByParams(obj) {
	  return request({
	    url: '/menulike',
	    method: 'post',
		data: obj
	  })
	},
	
  // 分页查询
  selpage4menulike(data) {
    return request({
      url: '/menulike/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4menulike(obj) {
    return request({
      url: '/menulike',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4menulike(id, obj) {
    return request({
      url: serverName + '//menulike/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menulike(id) {
    return request({
      url: serverName + '//menulike/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menulike(ids) {
    return request({
      url: serverName + '//menulike/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menulike(id) {
    return request({
      url: serverName + '//menulike/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//menulike/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//menulike/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menulike(params) {
    return request({
      url: serverName + '//menulike/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
