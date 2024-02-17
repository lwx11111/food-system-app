import request from '@/utils/request.js'


export default {

  // 分页查询
  selpage4menucollection(data) {
    return request({
      url:  '/menucollection/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4menucollection(obj) {
    return request({
      url:  '/menucollection',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4menucollection(id, obj) {
    return request({
      url:  '/menucollection/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menucollection(id) {
    return request({
      url:  '/menucollection/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menucollection(ids) {
    return request({
      url:  '/menucollection/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menucollection(id) {
    return request({
      url:  '/menucollection/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url:  '/menucollection/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url:  '/menucollection/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menucollection(params) {
    return request({
      url:  '/menucollection/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
