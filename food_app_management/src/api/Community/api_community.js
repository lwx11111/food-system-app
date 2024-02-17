import request from '@/utils/request.js'

export default {

  // 分页查询
  selpage4community(data) {
    return request({
      url: '/community/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4community(obj) {
    return request({
      url: '/community',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4community(id, obj) {
    return request({
      url: '/community/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4community(id) {
    return request({
      url: '/community/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4community(ids) {
    return request({
      url: '/community/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4community(id) {
    return request({
      url: '/community/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/community/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/community/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4community(params) {
    return request({
      url: '/community/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
