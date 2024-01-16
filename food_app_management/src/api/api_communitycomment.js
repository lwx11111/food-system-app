import request from '@/utils/request'

export default {

  // 分页查询
  selpage4communitycomment(data) {
    return request({
      url: '/communitycomment/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4communitycomment(obj) {
    return request({
      url: '/communitycomment',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4communitycomment(id, obj) {
    return request({
      url: '/communitycomment/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4communitycomment(id) {
    return request({
      url: '/communitycomment/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4communitycomment(ids) {
    return request({
      url: '/communitycomment/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4communitycomment(id) {
    return request({
      url: '/communitycomment/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/communitycomment/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/communitycomment/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4communitycomment(params) {
    return request({
      url: '/communitycomment/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
