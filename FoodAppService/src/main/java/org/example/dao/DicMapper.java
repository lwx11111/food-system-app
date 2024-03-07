package org.example.dao;

import org.example.domain.Dic;
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
 * 
 * @since 2024-01-22
 */
@Mapper
public interface DicMapper extends BaseMapper<Dic> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Dic>
    */
    IPage<Dic> selpageCustomSqlByWrapper(Page<Dic> page, @Param(Constants.WRAPPER)QueryWrapper<Dic> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Dic>
    */
    IPage<Dic> selpageCustomSqlByMap(Page<Dic> page, @Param("params") Map<String, String> params);
}
