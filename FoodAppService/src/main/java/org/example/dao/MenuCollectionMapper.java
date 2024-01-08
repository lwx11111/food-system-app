package org.example.dao;

import org.example.domain.MenuCollection;
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
 * @author lwx20
 * @since 2024-01-08
 */
@Mapper
public interface MenuCollectionMapper extends BaseMapper<MenuCollection> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<MenuCollection>
    */
    IPage<MenuCollection> selpageCustomSqlByWrapper(Page<MenuCollection> page, @Param(Constants.WRAPPER)QueryWrapper<MenuCollection> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<MenuCollection>
    */
    IPage<MenuCollection> selpageCustomSqlByMap(Page<MenuCollection> page, @Param("params") Map<String, String> params);
}
