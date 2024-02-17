import request from '@/utils/request.js'
export default {
  // 获取父类食物
  listParentDic() {
    return request({
      url: '/dic/listParentDic',
      method: 'get'
    })
  },

  // 分页查询
  selpage4dic(data) {
    return request({
      url: '/dic/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4dic(obj) {
    return request({
      url: '/dic',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4dic(id, obj) {
    return request({
      url: '/dic/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4dic(id) {
    return request({
      url: '/dic/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4dic(ids) {
    return request({
      url: '/dic/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4dic(id) {
    return request({
      url: '/dic/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: '/dic/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: '/dic/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4dic(params) {
    return request({
      url: '/dic/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
