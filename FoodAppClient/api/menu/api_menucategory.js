import request from '@/utils/request'


export default {

  // 分页查询
  selpage4menucategory(data) {
    return request({
      url: '/menucategory/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4menucategory(obj) {
    return request({
      url:  '/menucategory',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4menucategory(id, obj) {
    return request({
      url:  '/menucategory/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menucategory(id) {
    return request({
      url:  '/menucategory/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menucategory(ids) {
    return request({
      url:  '/menucategory/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menucategory(id) {
    return request({
      url:  '/menucategory/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url:  '/menucategory/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url:  '/menucategory/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menucategory(params) {
    return request({
      url:  '/menucategory/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
