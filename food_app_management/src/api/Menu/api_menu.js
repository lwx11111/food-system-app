import request from '@/utils/request.js'


export default {

  // 分页查询
  selpage4menu(data) {
    return request({
      url: '/menu/selpage',
      method: 'post',
      data: data
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

  // 修改
  update4menu(id, obj) {
    return request({
      url: '/menu/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menu(id) {
    return request({
      url: '/menu/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menu(ids) {
    return request({
      url: '/menu/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menu(id) {
    return request({
      url: '/menu/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/menu/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/menu/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menu(params) {
    return request({
      url: '/menu/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
