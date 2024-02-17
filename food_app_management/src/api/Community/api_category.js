import request from '@/utils/request.js'

export default {

  // 分页查询
  selpage4category(data) {
    return request({
      url: '/category/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4category(obj) {
    return request({
      url: '/category',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4category(id, obj) {
    return request({
      url: '/category/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4category(id) {
    return request({
      url: '/category/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4category(ids) {
    return request({
      url: '/category/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4category(id) {
    return request({
      url: '/category/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/category/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/category/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4category(params) {
    return request({
      url: '/category/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}