import request from '@/utils/request'
const serverName = '/shop'


export default {

  // 分页查询
  selpage4communitylike(data) {
    return request({
      url: serverName + '//communitylike/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4communitylike(obj) {
    return request({
      url: serverName + '//communitylike',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4communitylike(id, obj) {
    return request({
      url: serverName + '//communitylike/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4communitylike(id) {
    return request({
      url: serverName + '//communitylike/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4communitylike(ids) {
    return request({
      url: serverName + '//communitylike/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4communitylike(id) {
    return request({
      url: serverName + '//communitylike/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//communitylike/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//communitylike/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4communitylike(params) {
    return request({
      url: serverName + '//communitylike/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
