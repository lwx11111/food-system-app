import request from '@/utils/request'

export default {

  // 分页查询
  selpage4shopitemcategory(data) {
    return request({
      url: '/shopitemcategory/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4shopitemcategory(obj) {
    return request({
      url: '/shopitemcategory',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4shopitemcategory(id, obj) {
    return request({
      url: '/shopitemcategory/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4shopitemcategory(id) {
    return request({
      url: '/shopitemcategory/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4shopitemcategory(ids) {
    return request({
      url: '/shopitemcategory/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4shopitemcategory(id) {
    return request({
      url: '/shopitemcategory/' + id,
      method: 'get',
    })

  },

}
