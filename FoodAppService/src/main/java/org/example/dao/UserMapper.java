package org.example.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.domain.User;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @DataSource("auth_datasource")
    void list1();
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<User>
    */
    IPage<User> selpageCustomSqlByWrapper(Page<User> page, @Param(Constants.WRAPPER)QueryWrapper<User> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<User>
    */
    IPage<User> selpageCustomSqlByMap(Page<User> page, @Param("params") Map<String, String> params);
}
