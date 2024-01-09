package org.example.dao;

import org.example.domain.ShopOrder;
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
 * @since 2024-01-09
 */
@Mapper
public interface ShopOrderMapper extends BaseMapper<ShopOrder> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<ShopOrder>
    */
    IPage<ShopOrder> selpageCustomSqlByWrapper(Page<ShopOrder> page, @Param(Constants.WRAPPER)QueryWrapper<ShopOrder> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<ShopOrder>
    */
    IPage<ShopOrder> selpageCustomSqlByMap(Page<ShopOrder> page, @Param("params") Map<String, String> params);
}
