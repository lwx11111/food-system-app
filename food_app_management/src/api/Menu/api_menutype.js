import request from '@/utils/request'


export default {

  // 分页查询
  selpage4menutype(data) {
    return request({
      url: '/menutype/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4menutype(obj) {
    return request({
      url: '/menutype',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4menutype(id, obj) {
    return request({
      url: '/menutype/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menutype(id) {
    return request({
      url: '/menutype/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menutype(ids) {
    return request({
      url: '/menutype/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menutype(id) {
    return request({
      url: '/menutype/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/menutype/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/menutype/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menutype(params) {
    return request({
      url: '/menutype/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
