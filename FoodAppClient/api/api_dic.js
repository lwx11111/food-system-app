import request from '@/utils/request'


export default {
	listFoods(){
		return request({
		  url: '/dic/listFoods',
		  method: 'get',
		})
	},
  // 分页查询
  selpage4dic(data) {
    return request({
      url: '/dic/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4dic(obj) {
    return request({
      url: serverName + '//dic',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4dic(id, obj) {
    return request({
      url: serverName + '//dic/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4dic(id) {
    return request({
      url: serverName + '//dic/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4dic(ids) {
    return request({
      url: serverName + '//dic/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4dic(id) {
    return request({
      url: serverName + '//dic/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//dic/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//dic/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4dic(params) {
    return request({
      url: serverName + '//dic/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
