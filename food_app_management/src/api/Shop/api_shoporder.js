import request from '@/utils/request.js'



export default {

  // 分页查询
  selpage4shoporder(data) {
    return request({
      url: '/shoporder/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4shoporder(obj) {
    return request({
      url: '/shoporder',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4shoporder(id, obj) {
    return request({
      url: '/shoporder/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4shoporder(id) {
    return request({
      url: '/shoporder/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4shoporder(ids) {
    return request({
      url: '/shoporder/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4shoporder(id) {
    return request({
      url: '/shoporder/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/shoporder/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/shoporder/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4shoporder(params) {
    return request({
      url: '/shoporder/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
