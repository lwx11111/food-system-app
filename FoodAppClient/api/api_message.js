import request from '@/utils/request'

export default {

  // 分页查询
  selpage4message(data) {
    return request({
      url: '/message/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4message(obj) {
    return request({
      url: '/message',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4message(id, obj) {
    return request({
      url: '/message/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4message(id) {
    return request({
      url: '/message/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4message(ids) {
    return request({
      url: '/message/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4message(id) {
    return request({
      url: '/message/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/message/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/message/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4message(params) {
    return request({
      url: '/message/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
