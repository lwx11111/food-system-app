package org.example.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.domain.Category;
import org.example.domain.MenuCategory;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 * @since 2024-01-20
 */
@Mapper
public interface MenuCategoryMapper extends BaseMapper<MenuCategory> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Category>
    */
    IPage<MenuCategory> selpageCustomSqlByWrapper(Page<MenuCategory> page, @Param(Constants.WRAPPER)QueryWrapper<MenuCategory> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Category>
    */
    IPage<MenuCategory> selpageCustomSqlByMap(Page<MenuCategory> page, @Param("params") Map<String, String> params);
}
