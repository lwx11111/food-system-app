import request from '@/utils/request.js'


export default {

  // 分页查询
  selpage4menucomment(data) {
    return request({
      url: '/menucomment/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4menucomment(obj) {
    return request({
      url: '/menucomment',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4menucomment(id, obj) {
    return request({
      url: '/menucomment/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4menucomment(id) {
    return request({
      url: '/menucomment/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4menucomment(ids) {
    return request({
      url: '/menucomment/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4menucomment(id) {
    return request({
      url: '/menucomment/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/menucomment/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/menucomment/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4menucomment(params) {
    return request({
      url: '/menucomment/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
