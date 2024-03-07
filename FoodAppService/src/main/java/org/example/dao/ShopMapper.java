package org.example.dao;

import org.example.domain.Shop;
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
 * 店铺信息 Mapper 接口
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Shop>
    */
    IPage<Shop> selpageCustomSqlByWrapper(Page<Shop> page, @Param(Constants.WRAPPER)QueryWrapper<Shop> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Shop>
    */
    IPage<Shop> selpageCustomSqlByMap(Page<Shop> page, @Param("params") Map<String, String> params);
}
