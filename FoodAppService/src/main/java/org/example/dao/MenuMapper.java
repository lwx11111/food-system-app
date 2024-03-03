package org.example.dao;

import org.example.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜谱信息表 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    IPage<Menu> getDailyRecommendation(Page<Menu> page, @Param("value") String value);
    List<String> getRandomIds();
    IPage<Menu> getMenuCollectionByUserId(Page<Menu> page, @Param("userId") String userId);
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Menu>
    */
    IPage<Menu> selpageCustomSqlByWrapper(Page<Menu> page, @Param(Constants.WRAPPER)QueryWrapper<Menu> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Menu>
    */
    IPage<Menu> selpageCustomSqlByMap(Page<Menu> page, @Param("params") Map<String, String> params);
}
