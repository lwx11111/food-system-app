import request from '@/utils/request'


export default {

  // 分页查询
  selpage4shopitem(data) {
    return request({
      url: '/shopitem/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4shopitem(obj) {
    return request({
      url: '/shopitem',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4shopitem(id, obj) {
    return request({
      url: '/shopitem/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4shopitem(id) {
    return request({
      url: '/shopitem/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4shopitem(ids) {
    return request({
      url: '/shopitem/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4shopitem(id) {
    return request({
      url: '/shopitem/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/shopitem/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/shopitem/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4shopitem(params) {
    return request({
      url: '/shopitem/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
