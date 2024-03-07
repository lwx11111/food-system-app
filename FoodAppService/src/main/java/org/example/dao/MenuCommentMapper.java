package org.example.dao;

import org.example.domain.MenuComment;
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
 * 菜谱评论表 Mapper 接口
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@Mapper
public interface MenuCommentMapper extends BaseMapper<MenuComment> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<MenuComment>
    */
    IPage<MenuComment> selpageCustomSqlByWrapper(Page<MenuComment> page, @Param(Constants.WRAPPER)QueryWrapper<MenuComment> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<MenuComment>
    */
    IPage<MenuComment> selpageCustomSqlByMap(Page<MenuComment> page, @Param("params") Map<String, String> params);
}
