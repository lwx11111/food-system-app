package org.example.dao;

import org.example.domain.MenuType;
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
 *  Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2024-03-12
 */
@Mapper
public interface MenuTypeMapper extends BaseMapper<MenuType> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<MenuType>
    */
    IPage<MenuType> selpageCustomSqlByWrapper(Page<MenuType> page, @Param(Constants.WRAPPER)QueryWrapper<MenuType> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<MenuType>
    */
    IPage<MenuType> selpageCustomSqlByMap(Page<MenuType> page, @Param("params") Map<String, String> params);
}
