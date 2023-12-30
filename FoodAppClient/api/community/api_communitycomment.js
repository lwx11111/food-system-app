import request from '@/utils/request'
const serverName = '/shop'


export default {

  // 分页查询
  selpage4communitycomment(data) {
    return request({
      url: serverName + '//communitycomment/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4communitycomment(obj) {
    return request({
      url: serverName + '//communitycomment',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4communitycomment(id, obj) {
    return request({
      url: serverName + '//communitycomment/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4communitycomment(id) {
    return request({
      url: serverName + '//communitycomment/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4communitycomment(ids) {
    return request({
      url: serverName + '//communitycomment/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4communitycomment(id) {
    return request({
      url: serverName + '//communitycomment/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//communitycomment/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//communitycomment/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4communitycomment(params) {
    return request({
      url: serverName + '//communitycomment/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
