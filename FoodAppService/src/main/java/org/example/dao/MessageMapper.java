package org.example.dao;

import org.example.domain.Message;
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
 * @since 2024-02-16
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Message>
    */
    IPage<Message> selpageCustomSqlByWrapper(Page<Message> page, @Param(Constants.WRAPPER)QueryWrapper<Message> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Message>
    */
    IPage<Message> selpageCustomSqlByMap(Page<Message> page, @Param("params") Map<String, String> params);
}
