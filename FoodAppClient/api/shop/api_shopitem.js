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



}
