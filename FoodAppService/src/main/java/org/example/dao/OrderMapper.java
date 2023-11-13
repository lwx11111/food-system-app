package org.example.dao;

import org.example.domain.Order;
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
 * 点单表 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Order>
    */
    IPage<Order> selpageCustomSqlByWrapper(Page<Order> page, @Param(Constants.WRAPPER)QueryWrapper<Order> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Order>
    */
    IPage<Order> selpageCustomSqlByMap(Page<Order> page, @Param("params") Map<String, String> params);
}
