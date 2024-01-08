import request from '@/utils/request'
const serverName = '/shop'


export default {
	// 参数删除
	deleteMenuCollectionByParams(obj) {
	  return request({
	    url: '/menucollection',
	    method: 'post',
		data: obj
	  })
	},
  // 分页查询
  selpage4menucollection(data) {
    return request({
      url: '/menucollection/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4menucollection(obj) {
    return request({
      url: serverName + '//menucollection',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4menucollection(id, obj) {
    return request({
      url: serverName + '//menucollection/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menucollection(id) {
    return request({
      url: serverName + '//menucollection/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menucollection(ids) {
    return request({
      url: serverName + '//menucollection/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menucollection(id) {
    return request({
      url: serverName + '//menucollection/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//menucollection/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//menucollection/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menucollection(params) {
    return request({
      url: serverName + '//menucollection/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}