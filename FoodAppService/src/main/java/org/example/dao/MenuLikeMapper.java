package org.example.dao;

import org.example.domain.MenuLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

/**
 * <p>
 * 用户点赞表 Mapper 接口
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@Mapper
public interface MenuLikeMapper extends BaseMapper<MenuLike> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<MenuLike>
    */
    IPage<MenuLike> selpageCustomSqlByWrapper(Page<MenuLike> page, @Param(Constants.WRAPPER)QueryWrapper<MenuLike> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<MenuLike>
    */
    IPage<MenuLike> selpageCustomSqlByMap(Page<MenuLike> page, @Param("params") Map<String, String> params);
}
