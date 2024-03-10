import request from '@/utils/request'


export default {

  // 分页查询
  selpage4sysdict(data) {
    return request({
      url:  '/sysdict/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4sysdict(obj) {
    return request({
      url:  '/sysdict',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4sysdict(id, obj) {
    return request({
      url:  '/sysdict/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4sysdict(id) {
    return request({
      url:  '/sysdict/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4sysdict(ids) {
    return request({
      url:  '/sysdict/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4sysdict(id) {
    return request({
      url:  '/sysdict/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url:  '/sysdict/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url:  '/sysdict/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4sysdict(params) {
    return request({
      url:  '/sysdict/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
